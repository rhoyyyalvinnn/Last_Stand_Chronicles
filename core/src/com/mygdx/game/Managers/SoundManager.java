package com.mygdx.game.Managers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager {
    private static Music backgroundMusic;
    private static Music walkingEffect;



    public static void create() {
        if (backgroundMusic == null) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgmusic/menubgmusic.mp3"));
            backgroundMusic.setVolume(0.3f);
        }
    }
    //
    public static void playBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public static Music getBackgroundMusic(){
        return backgroundMusic;
    }
    public static void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
            backgroundMusic = null; // Set to null to ensure it's properly recreated if needed
        }
    }
}
