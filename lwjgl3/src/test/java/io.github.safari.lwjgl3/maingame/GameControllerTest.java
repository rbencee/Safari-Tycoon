package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.maingame.*;
import io.github.safari.lwjgl3.positionable.objects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerTest {
/*
    private GameController controller;
    private GameModel model;
    private Shop shop;
    private GameView view;

    @BeforeEach
    void setup() {
        model = new GameModel(1); // A megfelelő konstruktor, amit használsz
        shop = new Shop();
        view = new GameView(model); // vagy mock/stub, ha elérhető
        controller = new GameController(shop, model, view);
    }

    @Test
    void testTryToPlace_ValidEnvironment() {
        ShopItem item = new ShopItem("Tree", 100);
        shop.setShopItem(item);
        shop.setBuying(true);
        model.increasemoney(200); // biztosítsd, hogy van pénz

        boolean success = controller.TryToPlace(64, 64, 64, 64, 0, 0, false);

        assertTrue(success);
        List<Environment> envs = model.getEnvironments();
        assertEquals(1, envs.size());
        assertTrue(envs.get(0) instanceof Tree);
    }

    @Test
    void testTryToPlace_InsufficientFunds() {
        ShopItem item = new ShopItem("Tree", 1000); // drágább mint a pénz
        shop.setShopItem(item);
        shop.setBuying(true);
        model.increasemoney(100); // túl kevés pénz

        boolean success = controller.TryToPlace(64, 64, 64, 64, 0, 0, false);

        assertFalse(success);
        assertEquals(0, model.getEnvironments().size());
    }

    @Test
    void testSellEnvironment() {
        Tree tree = new Tree(new Position(64, 64, 64, 64));
        model.addtoenvironment(tree);

        ShopItem item = new ShopItem("Tree", 100);
        shop.setShopItem(item);
        shop.setBuying(false);

        controller.TryToPlace(64, 64, 64, 64, 0, 0, false);

        assertTrue(model.getEnvironments().isEmpty());
        assertEquals(70, model.getMoney()); // 100 * 0.7
    }



}
(/
 */
}
