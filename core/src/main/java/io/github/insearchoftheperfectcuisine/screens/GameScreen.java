package io.github.insearchoftheperfectcuisine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.insearchoftheperfectcuisine.core.GameConfig;
import io.github.insearchoftheperfectcuisine.systems.audio.AudioManager;
import io.github.insearchoftheperfectcuisine.systems.map.MapRenderer;

public class GameScreen implements Screen {
    float unitScale = 1/32f;

    GameConfig gameConfig;
    Music mainMusic;
    MapRenderer mapR;

    ExtendViewport viewport;
    OrthographicCamera camera;

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Prepare your screen here.
        gameConfig = new GameConfig();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10, 5 * (w / h));
        camera.update();
        viewport = new ExtendViewport(10, 5, camera);
        mapR = new MapRenderer(unitScale);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        // Draw your screen here. "delta" is the time since last render in seconds.
        viewport.apply();
        camera.update();
        mapR.render(camera);
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        camera.viewportWidth = 10f;                 // Viewport of 30 units!
        camera.viewportHeight = 5f * height/width; // Lets keep things in proportion.
        camera.update();
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        mapR.dispose();
        // Destroy screen's assets here.
    }
}
