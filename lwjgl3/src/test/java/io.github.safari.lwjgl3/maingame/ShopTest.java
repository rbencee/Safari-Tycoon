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


    @BeforeEach
    void setup() {
        Stage mockStage = mock(Stage.class);
        Skin mockSkin = mock(Skin.class);
        shop = new Shop(mockSkin, mockStage, new GameModel(1), 600);
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
