package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.safari.lwjgl3.maingame.*;
import io.github.safari.lwjgl3.util.ShopType;
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
