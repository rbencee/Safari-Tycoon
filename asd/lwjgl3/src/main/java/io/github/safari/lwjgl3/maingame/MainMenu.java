package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainMenu implements Screen {

    private Game game;
    private Skin skin;
    private Stage stage;


    public MainMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label title = new Label("Safari", skin);
        table.add(title);

        TextButton startButton = new TextButton("Start Game", skin);
        TextButton quitButton = new TextButton("Quit", skin);
        TextButton easyButton = new TextButton("Easy", skin);
        TextButton mediumButton = new TextButton("Medium", skin);
        TextButton hardButton = new TextButton("Hard", skin);

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame(1);
            }
        });

        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame(2);
            }
        });

        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame(3);
            }
        });

        table.row().pad(10);
        table.add(startButton);
        table.row().pad(10);
        table.add(quitButton);

        table.row().pad(10);
        table.add(easyButton);
        table.add(mediumButton);
        table.add(hardButton);

        stage.addActor(table);
    }

    private void startGame(int diff) {
        System.out.println("Starting game with difficulty: " + diff);

        game.setScreen(new GameView(diff));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose(); // Erőforrások felszabadítása
        skin.dispose();  // Skin felszabadítása
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
