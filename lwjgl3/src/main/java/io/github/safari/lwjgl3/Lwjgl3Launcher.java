package io.github.safari.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.safari.lwjgl3.maingame.MainMenu;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new MyGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Safari");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(1920, 1080);
        configuration.setWindowIcon("libgdx128.png");
        return configuration;
    }

    private static class MyGame extends Game {
        private Music music;

        @Override
        public void create() {
            this.setScreen(new MainMenu(this));

            music = Gdx.audio.newMusic(Gdx.files.internal("music/my_song.mp3"));

            music.setLooping(true);
            music.setVolume(0.4f);
            music.play();


        }

        @Override
        public void render() {
            super.render();
        }

        @Override
        public void dispose() {
            if (music != null) {
                music.stop();
                music.dispose();
            }
        }
    }
}
