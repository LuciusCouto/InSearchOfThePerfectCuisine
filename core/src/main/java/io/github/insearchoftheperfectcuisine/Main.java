package io.github.insearchoftheperfectcuisine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import io.github.insearchoftheperfectcuisine.core.GameConfig;
import io.github.insearchoftheperfectcuisine.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    GameConfig gameConfig;

    @Override
    public void create() {
        gameConfig = new GameConfig();
        GameConfig.applyAllSettings();

        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        Gdx.app.log("Fps: ", Integer.toString(Gdx.graphics.getFramesPerSecond()));
        // Chama o método de renderização da tela atual
        super.render();
    }

    @Override
    public void dispose() {
        // Limpeza dos recursos, se necessário
        getScreen().dispose();
    }
}
