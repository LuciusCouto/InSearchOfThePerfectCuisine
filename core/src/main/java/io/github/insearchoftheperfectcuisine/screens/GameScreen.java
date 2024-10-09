package io.github.insearchoftheperfectcuisine.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import io.github.insearchoftheperfectcuisine.core.GameConfig;
import io.github.insearchoftheperfectcuisine.systems.map.MapRenderer;

public class GameScreen implements Screen {
    float unitScale = 1 / 32f;

    GameConfig gameConfig;
    Music mainMusic;
    MapRenderer mapR;
    private World world;

    ExtendViewport viewport;
    OrthographicCamera camera;
    Box2DDebugRenderer debugRenderer;

    @Override
    public void show() {
        Box2D.init();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Prepare your screen here.
        gameConfig = new GameConfig();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10, 5 * (w / h));
        camera.update();
        viewport = new ExtendViewport(20, 10, camera);
        world = new World(new Vector2(0, 0f), true);
        mapR = new MapRenderer(unitScale, world);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        // Atualiza o mundo do Box2D
        world.step(1/60f, 6, 2);

        // Atualiza o jogador

        // Faz a câmera seguir o jogador
        camera.update();

        // Renderiza o mapa
        viewport.apply();
        camera.update();
        mapR.render(camera);
        debugRenderer.render(world, camera.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom += 1 * unitScale;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 1 * unitScale;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            mapR.loadMap("maps/teste.tmx");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            mapR.loadMap("maps/teste2.tmx");
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 10f;
        camera.viewportHeight = 5f * height / width;
        camera.update();
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        mapR.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
