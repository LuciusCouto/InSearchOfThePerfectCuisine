package io.github.insearchoftheperfectcuisine.systems.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderer {
    public OrthogonalTiledMapRenderer renderer;
    public TiledMap map;
    float unitScale;
    public MapRenderer(float unitScale) {
        this.unitScale = unitScale;
        map = new TmxMapLoader().load("maps/teste.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, this.unitScale);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        renderer.dispose();
        map.dispose();
    }
}
