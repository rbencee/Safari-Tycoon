package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.maingame.*;
import io.github.safari.lwjgl3.util.ShopType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private Shop shop;

    @BeforeEach
    void setup() {
        GameModel dummy = new GameModel(1);
        shop = new Shop(null, null, dummy);
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
