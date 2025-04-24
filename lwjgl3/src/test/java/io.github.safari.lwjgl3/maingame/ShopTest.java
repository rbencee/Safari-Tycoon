package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.safari.lwjgl3.maingame.*;
import io.github.safari.lwjgl3.util.ShopType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ShopTest {

    private Shop shop;


    @BeforeAll
    static void initGdxStubs() {
        Gdx.graphics = new Graphics() {
            @Override
            public boolean isGL30Available() {
                return false;
            }

            @Override
            public boolean isGL31Available() {
                return false;
            }

            @Override
            public boolean isGL32Available() {
                return false;
            }

            @Override
            public GL20 getGL20() {
                return null;
            }

            @Override
            public GL30 getGL30() {
                return null;
            }

            @Override
            public GL31 getGL31() {
                return null;
            }

            @Override
            public GL32 getGL32() {
                return null;
            }

            @Override
            public void setGL20(GL20 gl20) {

            }

            @Override
            public void setGL30(GL30 gl30) {

            }

            @Override
            public void setGL31(GL31 gl31) {

            }

            @Override
            public void setGL32(GL32 gl32) {

            }

            @Override
            public int getWidth() {
                return 0;
            }

            @Override public int getHeight() { return 600; }

            @Override
            public int getBackBufferWidth() {
                return 0;
            }

            @Override
            public int getBackBufferHeight() {
                return 0;
            }

            @Override
            public float getBackBufferScale() {
                return 0;
            }

            @Override
            public int getSafeInsetLeft() {
                return 0;
            }

            @Override
            public int getSafeInsetTop() {
                return 0;
            }

            @Override
            public int getSafeInsetBottom() {
                return 0;
            }

            @Override
            public int getSafeInsetRight() {
                return 0;
            }

            @Override
            public long getFrameId() {
                return 0;
            }

            @Override
            public float getDeltaTime() {
                return 0;
            }

            @Override
            public float getRawDeltaTime() {
                return 0;
            }

            @Override
            public int getFramesPerSecond() {
                return 0;
            }

            @Override
            public GraphicsType getType() {
                return null;
            }

            @Override
            public GLVersion getGLVersion() {
                return null;
            }

            @Override
            public float getPpiX() {
                return 0;
            }

            @Override
            public float getPpiY() {
                return 0;
            }

            @Override
            public float getPpcX() {
                return 0;
            }

            @Override
            public float getPpcY() {
                return 0;
            }

            @Override
            public float getDensity() {
                return 0;
            }

            @Override
            public boolean supportsDisplayModeChange() {
                return false;
            }

            @Override
            public Monitor getPrimaryMonitor() {
                return null;
            }

            @Override
            public Monitor getMonitor() {
                return null;
            }

            @Override
            public Monitor[] getMonitors() {
                return new Monitor[0];
            }

            @Override
            public DisplayMode[] getDisplayModes() {
                return new DisplayMode[0];
            }

            @Override
            public DisplayMode[] getDisplayModes(Monitor monitor) {
                return new DisplayMode[0];
            }

            @Override
            public DisplayMode getDisplayMode() {
                return null;
            }

            @Override
            public DisplayMode getDisplayMode(Monitor monitor) {
                return null;
            }

            @Override
            public boolean setFullscreenMode(DisplayMode displayMode) {
                return false;
            }

            @Override
            public boolean setWindowedMode(int i, int i1) {
                return false;
            }

            @Override
            public void setTitle(String s) {

            }

            @Override
            public void setUndecorated(boolean b) {

            }

            @Override
            public void setResizable(boolean b) {

            }

            @Override
            public void setVSync(boolean b) {

            }

            @Override
            public void setForegroundFPS(int i) {

            }

            @Override
            public BufferFormat getBufferFormat() {
                return null;
            }

            @Override
            public boolean supportsExtension(String s) {
                return false;
            }

            @Override
            public void setContinuousRendering(boolean b) {

            }

            @Override
            public boolean isContinuousRendering() {
                return false;
            }

            @Override
            public void requestRendering() {

            }

            @Override
            public boolean isFullscreen() {
                return false;
            }

            @Override
            public Cursor newCursor(Pixmap pixmap, int i, int i1) {
                return null;
            }

            @Override
            public void setCursor(Cursor cursor) {

            }

            @Override
            public void setSystemCursor(Cursor.SystemCursor systemCursor) {

            }

        };
    }


    @BeforeEach
    void setup() {
        Stage mockStage = mock(Stage.class);
        Skin mockSkin = mock(Skin.class);
        shop = new Shop(mockSkin, mockStage, new GameModel(1));
    }

    @Test
    void testInitialSelectionIsNull() {
        assertNull(shop.getShopItems(), "First Selected Item should be null");
    }

    @Test
    void testBuyingFlagDefaultTrue() {
        assertTrue(shop.isBuying(), "First we should be buying");
    }

    @Test
    void testClearSelection() {
        shop.clearSelection();
        assertNull(shop.getShopItems());
    }
}
