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
        shop = new Shop(null, null, new GameModel(1));
    }

    @Test
    void testInitialSelectionIsNull() {
        assertNull(shop.getShopItems(), "A kezdeti kiválasztott elemnek null-nak kell lennie.");
    }

    @Test
    void testBuyingFlagDefaultTrue() {
        assertTrue(shop.isBuying(), "A vásárlás alapértelmezetten be kell legyen kapcsolva.");
    }

    @Test
    void testClearSelection() {
        shop.clearSelection();
        assertNull(shop.getShopItems());
    }
}
