package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;

public class LibGDXHeadlessTest {

    private static HeadlessApplication application;

    @BeforeAll
    public static void init() {
        if (application == null) {
            application = new HeadlessApplication(new ApplicationListener() {
                @Override public void create() {}
                @Override public void resize(int width, int height) {}
                @Override public void render() {}
                @Override public void pause() {}
                @Override public void resume() {}
                @Override public void dispose() {}
            }, new HeadlessApplicationConfiguration());
        }
    }
}
