package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private Shop shop;
    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        // Mockito használata a teszteléshez
        gameModel = mock(GameModel.class);
        Skin mockSkin = mock(Skin.class);
        Stage mockStage = mock(Stage.class);
        shop = new Shop(mockSkin, mockStage, gameModel);
    }

    @Test
    void testShowPlantsPage() {
        shop.showPlantsPage();

        // Ellenőrizzük, hogy a Plants gombok betöltődtek
        assertEquals(4, shop.getContentTable().getChildren().size); // 4 gomb (Tree, Lake, Bush, Grass)

        // Ellenőrizzük, hogy az első gomb "Tree"-t tartalmaz
        TextButton treeButton = (TextButton) shop.getContentTable().getChildren().get(0);
        assertTrue(treeButton.getLabel().getText().toString().contains("Tree"));
    }

    @Test
    void testSelectItem() {
        ShopItem item = new ShopItem("Tree", 50);
        shop.updateContent(new ShopItem[]{item});

        // Tegyük ki az Item gombot és válasszuk ki
        TextButton treeButton = (TextButton) shop.getContentTable().getChildren().get(0);
        treeButton.fire(new ClickListener());

        // Ellenőrizzük, hogy a megfelelő item van kiválasztva
        assertEquals(item, shop.getShopItems());
        assertTrue(shop.isBuying());
    }

    @Test
    void testChangeTicketPrice() {
        // Szimuláljuk a ticket price változtatást
        TextButton changePriceButton = shop.createChangePriceButton();
        changePriceButton.fire(new ClickListener());

        // Ellenőrizzük, hogy a ticket price valóban változott
        verify(gameModel, times(1)).ChangeTicketPrice(anyInt());
    }
}
