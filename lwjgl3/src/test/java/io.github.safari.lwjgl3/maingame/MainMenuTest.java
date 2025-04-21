package io.github.safari.lwjgl3.maingame;

/*
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuTest {

    private MainMenu mainMenu;
    private Game game;

    @BeforeEach
    void setUp() {
        game = mock(Game.class); // Mockoljunk egy Game objektumot
        mainMenu = new MainMenu(game);
    }

    @Test
    void testStartGameWithEasyDifficulty() {
        TextButton easyButton = mock(TextButton.class);
        when(easyButton.getText()).thenReturn("Easy");

        mainMenu.startGame(1);  // Indítjuk a játékot Easy szinttel

        // Ellenőrizzük, hogy a game.setScreen() meghívódik a megfelelő paraméterekkel
        verify(game).setScreen(any(GameView.class));
    }

    @Test
    void testGetDifficulty() {
        TextButton easyButton = mock(TextButton.class);
        when(easyButton.getText()).thenReturn("Easy");
        int difficulty = mainMenu.getDifficulty(easyButton);

        assertEquals(1, difficulty);  // Ellenőrizzük, hogy a megfelelő nehézségi szintet adja vissza
    }

    @Test
    void testDifficultyButtonSelection() {
        TextButton easyButton = new TextButton("Easy", skin);
        easyButton.setChecked(true);

        // Simuláljuk a gombnyomást
        easyButton.fire(new ClickListener());

        // Ellenőrizzük, hogy a gombok színe megváltozik
        assertEquals(Color.YELLOW, easyButton.getStyle().fontColor);
    }

    @Test
    void testStartButtonClick() {
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenu.startGame(2);  // Például Medium nehézségi szint
            }
        });

        // Simuláljuk a gomb kattintását
        startButton.fire(new ClickListener());

        // Ellenőrizzük, hogy elindul-e a játék megfelelő nehézséggel
        verify(game).setScreen(any(GameView.class));
    }


}
*/
