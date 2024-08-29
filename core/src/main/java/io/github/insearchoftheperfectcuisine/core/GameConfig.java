package io.github.insearchoftheperfectcuisine.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameConfig {

    // Constantes padrão
    public static final int DEFAULT_SCREEN_WIDTH = 680;
    public static final int DEFAULT_SCREEN_HEIGHT = 400;
    public static final boolean DEFAULT_IS_FULLSCREEN = false;
    public static final boolean DEFAULT_IS_VSYNC = false;
    public static final float DEFAULT_FRAME_RATE = 120.0f;
    public static final float DEFAULT_MUSIC_VOLUME = 1.0f;
    public static final float DEFAULT_SOUND_VOLUME = 0.8f;
    public static final String DEFAULT_LANGUAGE = "pt-br";

    // Variáveis de configuração
    public static int screenWidth = DEFAULT_SCREEN_WIDTH;
    public static int screenHeight = DEFAULT_SCREEN_HEIGHT;
    public static boolean isFullscreen = DEFAULT_IS_FULLSCREEN;
    public static boolean isVsync = DEFAULT_IS_VSYNC;
    public static float frameRate = DEFAULT_FRAME_RATE;
    public static float musicVolume = DEFAULT_MUSIC_VOLUME;
    public static float soundVolume = DEFAULT_SOUND_VOLUME;
    public static String language = DEFAULT_LANGUAGE;

    // Nome do arquivo de preferências
    private static final String PREFERENCES_NAME = "game_config";

    // Carrega as configurações
    public static void load() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);

        screenWidth = prefs.getInteger("screenWidth", DEFAULT_SCREEN_WIDTH);
        screenHeight = prefs.getInteger("screenHeight", DEFAULT_SCREEN_HEIGHT);
        isFullscreen = prefs.getBoolean("isFullscreen", DEFAULT_IS_FULLSCREEN);
        isVsync = prefs.getBoolean("isVsync", DEFAULT_IS_VSYNC);
        frameRate = prefs.getFloat("frameRate", DEFAULT_FRAME_RATE);
        musicVolume = prefs.getFloat("musicVolume", DEFAULT_MUSIC_VOLUME);
        soundVolume = prefs.getFloat("soundVolume", DEFAULT_SOUND_VOLUME);
        language = prefs.getString("language", DEFAULT_LANGUAGE);
    }

    // Salva as configurações
    public static void save() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);

        prefs.putInteger("screenWidth", screenWidth);
        prefs.putInteger("screenHeight", screenHeight);
        prefs.putBoolean("isFullscreen", isFullscreen);
        prefs.putBoolean("isVsync", DEFAULT_IS_VSYNC);
        prefs.putFloat("frameRate", frameRate);
        prefs.putFloat("musicVolume", musicVolume);
        prefs.putFloat("soundVolume", soundVolume);
        prefs.putString("language", language);

        prefs.flush();
    }

    public static void applyAllSettings() {
        applyGraphicsSettings();
        applyAudioSettings();
        applyLanguageSettings();
    }

    // Aplica configurações de gráficos
    public static void applyGraphicsSettings() {
        // Configura a resolução da tela
        if (isFullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
        }

        // Configura VSync
        Gdx.graphics.setVSync(isVsync);

        // Configura o frame rate
        Gdx.graphics.setForegroundFPS((int) frameRate);
    }

    // Aplica configurações de áudio
    public static void applyAudioSettings() {
        // Ajustar o volume da música
        // Por exemplo, se estiver usando um gerenciador de áudio
        // musicPlayer.setVolume(musicVolume);

        // Ajustar o volume dos efeitos sonoros
        // soundManager.setVolume(soundVolume);
    }

    // Aplica configurações de linguagem
    public static void applyLanguageSettings() {
        // Aqui você pode ajustar o idioma do jogo, por exemplo:
        // LanguageManager.setLanguage(language);
    }

    // Restaura as configurações padrão
    public static void restoreDefaultSettings() {
        screenWidth = DEFAULT_SCREEN_WIDTH;
        screenHeight = DEFAULT_SCREEN_HEIGHT;
        isFullscreen = DEFAULT_IS_FULLSCREEN;
        isVsync = DEFAULT_IS_VSYNC;
        frameRate = DEFAULT_FRAME_RATE;
        musicVolume = DEFAULT_MUSIC_VOLUME;
        soundVolume = DEFAULT_SOUND_VOLUME;
        language = DEFAULT_LANGUAGE;

        // Salvar as configurações padrão
        save();
    }
}
