package io.github.insearchoftheperfectcuisine.systems.collision;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
        float offsetX = 0f;
        float offsetY = 0f;

        // Define a posição do corpo com base nas propriedades ajustadas
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + offsetX + tileWidth / 2, y + offsetY + tileHeight / 2);
        Body body = world.createBody(bodyDef);

        // Cria a forma da colisão baseada nas dimensões ajustadas
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(tileWidth / 2, tileHeight / 2); // Utiliza tileWidth e tileHeight para definir a forma

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

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

        body.createFixture(fixtureDef);
        shape.dispose(); // Libera os recursos da forma após a utilização
    }
}
