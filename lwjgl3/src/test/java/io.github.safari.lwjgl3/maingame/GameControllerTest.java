package io.github.safari.lwjgl3.maingame;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private Shop shop;
    private GameModel model;
    private GameView view;
    private GameController controller;

    @BeforeEach
    public void setUp() {
        shop = mock(Shop.class);
        model = new GameModel(1);
        view = mock(GameView.class);

        controller = new GameController(shop, model, view);
    }

    @Test
    void testTryToPlace_NoItemSelected_ReturnsFalse() {
        when(shop.getShopItems()).thenReturn(null);
        boolean result = controller.TryToPlace(100, 100, 64, 64, 0, 0, false);
        assertFalse(result);
    }

    @Test
    void testTryToPlace_InsufficientFunds_ThrowsException() {
        ShopItem item = mock(ShopItem.class);
        when(shop.getShopItems()).thenReturn(item);
        when(shop.isBuying()).thenReturn(true);
        when(item.getName()).thenReturn("Tree");
        when(item.getPrice()).thenReturn(954817);
        model.increasemoney(10);

        boolean result = controller.TryToPlace(0, 0, 64, 64, 0, 0, false);
        assertFalse(result);
        assertTrue(false);
    }

}
