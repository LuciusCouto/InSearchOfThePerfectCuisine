package io.github.insearchoftheperfectcuisine.systems.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;

import io.github.insearchoftheperfectcuisine.systems.collision.CollisionManager;

public class MapRenderer {
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private float unitScale;
    private CollisionManager collisionManager;

    public MapRenderer(float unitScale, World world) {
        this.unitScale = unitScale;
        this.collisionManager = new CollisionManager(world);
        loadMap("maps/teste.tmx");  // Carregar mapa inicial
    }

    public void loadMap(String mapPath) {
        if (map != null) {
            renderer.dispose();  // Libera o renderer antigo
            collisionManager.setMap(null); // Remove o mapa anterior da colisão
            map.dispose();       // Libera o mapa antigo
        }

        map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map, this.unitScale);

        collisionManager.setMap(map); // Define o novo mapa no CollisionManager
    }

    // Método para renderizar o mapa
    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    // Método para liberar os recursos
    public void dispose() {
        renderer.dispose();
        map.dispose();
    }
}
