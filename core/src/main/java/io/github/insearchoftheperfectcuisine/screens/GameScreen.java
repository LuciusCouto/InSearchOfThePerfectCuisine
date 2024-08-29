package io.github.insearchoftheperfectcuisine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import io.github.insearchoftheperfectcuisine.core.GameConfig;
import io.github.insearchoftheperfectcuisine.systems.audio.AudioManager;

public class GameScreen implements Screen {
    GameConfig gameConfig;
    Music mainMusic;

    @Override
    public void show() {
        // Prepare your screen here.
        gameConfig = new GameConfig();


    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
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
        // Destroy screen's assets here.
    }
}
