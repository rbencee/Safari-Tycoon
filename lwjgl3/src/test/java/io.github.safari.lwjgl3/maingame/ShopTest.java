package io.github.safari.lwjgl3.maingame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ShopTest {

    private Shop shop;
    private Skin skin;
    private Stage stage;
    private GameModel gameModel;
    /*

    @BeforeEach
    public void setUp() {
        // Initialize headless application
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), config);

        // Mock GL20 since it's not available in headless mode
        Gdx.gl = Gdx.gl20 = new GL20() {
            @Override public void glClear(int mask) {}
            @Override public void glClearColor(float red, float green, float blue, float alpha) {}
            // Implement other required methods...
        };

        skin = new Skin();
        stage = new Stage(); // Now this will work because Gdx.graphics is initialized
        gameModel = new GameModel(1);
        shop = new Shop(skin, stage, gameModel, 1080f);
    }

    @Test
    public void testVisibilityMethods() {
        assertFalse(shop.isVisible());
        shop.show();
        assertTrue(shop.isVisible());
        shop.hide();
        assertFalse(shop.isVisible());
    }

    @Test
    public void testGetShopItemsWhenNothingSelected() {
        assertNull(shop.getShopItems());
    }

    @Test
    public void testIsBuying() {
        assertTrue(shop.isBuying());
    }

    @Test
    public void testClearSelection() {
        assertNull(shop.getShopItems());
    }

    @Test
    public void testSetShopItem() {
        ShopItem testItem = new ShopItem("TestItem", 100);
        shop.setShopItem(testItem);
    }

    // Empty application listener for headless mode
    private static class EmptyApplicationListener implements com.badlogic.gdx.ApplicationListener {
        @Override public void create() {}
        @Override public void resize(int width, int height) {}
        @Override public void render() {}
        @Override public void pause() {}
        @Override public void resume() {}
        @Override public void dispose() {}
    }

     */
}
