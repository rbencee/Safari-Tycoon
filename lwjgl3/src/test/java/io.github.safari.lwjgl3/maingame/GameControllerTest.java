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



}
