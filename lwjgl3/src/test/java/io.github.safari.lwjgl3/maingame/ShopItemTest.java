package io.github.safari.lwjgl3.maingame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShopItemTest {

    @Test
    void testConstructorAndGetters() {
        ShopItem item = new ShopItem("Tree", 100);

        assertEquals("Tree", item.getName(), "Name should be 'Tree'");
        assertEquals(100, item.getPrice(), "Price should be 100");
    }
}
