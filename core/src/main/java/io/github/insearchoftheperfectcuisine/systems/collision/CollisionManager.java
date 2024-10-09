package io.github.insearchoftheperfectcuisine.systems.collision;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CollisionManager {
    private World world;
    private TiledMap map;

    // Camadas sólidas (nomes das camadas que você deseja considerar como sólidas)
    private String[] solidLayerNames = {"parede", "paredeTop"};

    public CollisionManager(World world) {
        this.world = world;
    }

    public void setMap(TiledMap map) {
        this.map = map;
        if (map != null) {
            createCollisionBodies();
        }
    }

    private void createCollisionBodies() {
        for (String layerName : solidLayerNames) {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);
            if (layer != null) {
                for (int y = 0; y < layer.getHeight(); y++) {
                    for (int x = 0; x < layer.getWidth(); x++) {
                        TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                        if (cell != null && cell.getTile() != null) {
                            // Verifique as propriedades do tile
                            MapProperties tileProperties = cell.getTile().getProperties();

                            if (tileProperties.containsKey("collisionType")) {
                                String collisionType = tileProperties.get("collisionType", String.class);
                                // Crie colisões personalizadas com base no tipo de colisão
                                createBodyForTile(x, y, tileProperties, collisionType, cell.getTile());
                            } else {
                                // Caso não tenha uma propriedade definida, aplique a colisão padrão
                                createBodyForTile(x, y, tileProperties, "solid", cell.getTile());
                            }
                        }
                    }
                }
            }
        }
    }

    private void createBodyForTile(int x, int y, MapProperties tileProperties, String collisionType, TiledMapTile tile) {
        float scale = 1 / 32f; // Ajuste a escala se necessário
        float tileWidth = 1f;
        float tileHeight = 1f;

        // Define a posição do corpo com base nas propriedades ajustadas
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + tileWidth / 2, y + tileHeight / 2);
        Body body = world.createBody(bodyDef);

        boolean customShapeCreated = false;

        // Verifica se o tile possui uma forma de colisão personalizada como RectangleMapObject
        if (tile.getObjects().getCount() > 0) {
            for (MapObject object : tile.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;
                    Rectangle rect = rectangleObject.getRectangle();

                    // Obtém a rotação do objeto a partir das propriedades
                    float rotation = 0;
                    if (rectangleObject.getProperties().containsKey("rotation")) {
                        rotation = (float) Math.toRadians((float) rectangleObject.getProperties().get("rotation"));
                    }

                    // Define as dimensões do retângulo
                    float rectWidth = rect.width * scale / 2;
                    float rectHeight = rect.height * scale / 2;

                    // Calcula a posição do retângulo relativo ao canto superior esquerdo do tile
                    float rectX = (rect.x * scale) - (tileWidth / 2) + rectWidth;
                    float rectY = (rect.y * scale) - (tileHeight / 2) + rectHeight;

                    // Cria a forma como um retângulo e aplica a rotação correta
                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectWidth, rectHeight, new Vector2(rectX, rectY), rotation);

                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    applyCollisionProperties(fixtureDef, collisionType);

                    body.createFixture(fixtureDef);
                    shape.dispose(); // Libera os recursos
                    customShapeCreated = true;
                }
            }
        }

        // Se nenhuma forma personalizada foi encontrada, aplica a colisão padrão
        if (!customShapeCreated) {
            PolygonShape defaultShape = new PolygonShape();
            defaultShape.setAsBox(tileWidth / 2, tileHeight / 2); // Utiliza tileWidth e tileHeight para definir a forma

            FixtureDef defaultFixtureDef = new FixtureDef();
            defaultFixtureDef.shape = defaultShape;
            applyCollisionProperties(defaultFixtureDef, collisionType);

            body.createFixture(defaultFixtureDef);
            defaultShape.dispose(); // Libera os recursos da forma após a utilização
        }
    }

    private void applyCollisionProperties(FixtureDef fixtureDef, String collisionType) {
        switch (collisionType) {
            case "bouncy":
                fixtureDef.restitution = 1f;
                fixtureDef.friction = 0f;
                break;
            case "slippery":
                fixtureDef.friction = 0.1f;
                break;
            case "solid":
            default:
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0f;
                break;
        }
    }
}
