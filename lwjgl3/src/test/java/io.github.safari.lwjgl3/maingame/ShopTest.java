import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.maingame.Shop;
import io.github.safari.lwjgl3.maingame.ShopItem;
import io.github.safari.lwjgl3.util.ShopType;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    private static HeadlessApplication application;
    private Shop shop;
    private Skin skin;
    private Stage stage;
    private GameModel mockGameModel;

    @BeforeAll
    static void init() {
        application = new HeadlessApplication(new ApplicationAdapter() {}, new HeadlessApplicationConfiguration());
    }

    @AfterAll
    static void cleanUp() {
        application.exit();
    }

    @BeforeEach
    void setUp() {
        stage = new Stage();
        skin = new Skin();
        mockGameModel = new GameModel(1);
        Stage mockStage = Mockito.mock(Stage.class);
        shop = new Shop(skin, mockStage, mockGameModel, 800);
    }

    @Test
    void testShowAndHide() {
        assertFalse(shop.isVisible());
        shop.show();
        assertTrue(shop.isVisible());
        shop.hide();
        assertFalse(shop.isVisible());
    }

    @Test
    void testInitialPageShownIsPlants() {
        assertEquals(ShopType.PLANTS, shop.getPageShown());
    }

    @Test
    void testClearSelection() {
        shop.clearSelection();
        assertNull(shop.getShopItems());
    }

    @Test
    void testIsBuyingDefaultTrue() {
        assertTrue(shop.isBuying());
    }

    @Test
    void testGetAllItemButtonsNotEmptyAfterShow() {
        shop.show();
        assertFalse(shop.getAllItemButtons().isEmpty());
    }
}
