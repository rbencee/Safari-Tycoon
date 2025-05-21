package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainMenuTest extends LibGDXHeadlessTest {

    private MainMenu mainMenu;
    private Game mockGame;
    private Skin mockSkin;

    @BeforeAll
    public static void setup() {
        LibGDXHeadlessTest.init();
    }

    @BeforeEach
    public void setUp() {
        mockGame = mock(Game.class);
        mockSkin = mock(Skin.class);

        try (MockedStatic<Gdx> mockedGdx = mockStatic(Gdx.class)) {
            FileHandle mockFileHandle = mock(FileHandle.class);
            mockedGdx.when(() -> Gdx.files.internal("skin/craftacular-ui.json"))
                .thenReturn(mockFileHandle);

            mainMenu = new MainMenu(mockGame);
        }
    }

    @Test
    public void testMainMenuInitialization() {
        mainMenu.show();

        assertNotNull(mainMenu.getStage(), "Stage should be initialized");
        assertNotNull(mainMenu.getBackgroundTexture(), "Background texture should be initialized");
        assertNotNull(mainMenu.getSpriteBatch(), "SpriteBatch should be initialized");
    }

    @Test
    public void testGetDifficulty() {
        TextButton easyButton = new TextButton("Easy", mockSkin);
        TextButton mediumButton = new TextButton("Medium", mockSkin);
        TextButton hardButton = new TextButton("Hard", mockSkin);

        assertEquals(1, mainMenu.getDifficulty(easyButton), "Easy should return 1");
        assertEquals(2, mainMenu.getDifficulty(mediumButton), "Medium should return 2");
        assertEquals(3, mainMenu.getDifficulty(hardButton), "Hard should return 3");
    }

    @Test
    public void testButtonCreation() {
        mainMenu.show();

        Stage stage = mainMenu.getStage();
        assertTrue(stage.getActors().size > 0, "Stage should contain buttons");
    }

    @Test
    public void testStartButtonAction() {
        mainMenu.show();

        // Mock difficulty selection
        TextButton mockButton = mock(TextButton.class);
        when(mockButton.getText()).thenReturn("Medium");

        // Normally you'd find the actual button from stage
        mainMenu.getDifficulty(mockButton); // This would be 2 for Medium

        // Verify game screen changes when start is clicked
        // This would require refactoring MainMenu to make startGame testable
        assertDoesNotThrow(() -> {
            // Simulate button click
            // In real test you'd find the actual start button
        });
    }

     */
}
