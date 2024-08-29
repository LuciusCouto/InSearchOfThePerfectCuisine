package io.github.insearchoftheperfectcuisine.systems.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

import io.github.insearchoftheperfectcuisine.core.GameConfig;

public class AudioManager {
    private static final AudioManager instance = new AudioManager();
    private static float musicVolume = 0.7f;
    private float soundVolume = 0.8f;
    private static Array<Music> musics = new Array<>();
    private Array<Sound> sounds = new Array<>();

    // Singleton pattern
    public static AudioManager getInstance() {
        return instance;
    }

    private AudioManager() {}

    // Adiciona música ao gerenciador
    public static void addMusic(Music music) {
        musics.add(music);
        music.setVolume(musicVolume);
    }

    // Adiciona som ao gerenciador
    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    // Reproduz a música
    public static void playMusic(Music music) {
        music.setVolume(musicVolume);
        music.play();
    }

    // Reproduz o som
    public void playSound(Sound sound) {
        sound.play(soundVolume);
    }

    // Configura o volume da música
    public void setMusicVolume(float volume) {
        musicVolume = volume;
        for (Music music : musics) {
            music.setVolume(musicVolume);
        }
    }

    // Configura o volume do som
    public void setSoundVolume(float volume) {
        soundVolume = volume;
    }

    // Aplica as configurações de áudio
    public void applyAudioSettings() {
        setMusicVolume(GameConfig.musicVolume);
        setSoundVolume(GameConfig.soundVolume);
    }
}
