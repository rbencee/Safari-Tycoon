package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;



public class MainMenu implements Screen {

    private Game game;
    private Skin skin;
    private Stage stage;
    private ButtonGroup<TextButton> difficultyGroup;
    private Texture backgroundTexture;
    private SpriteBatch spriteBatch;


    public MainMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        ShowBackGround();
        Table table = new Table();
        table.center();
        table.setFillParent(true);



        TextButton startButton = new TextButton("Start Game", skin);
        TextButton quitButton = new TextButton("Quit", skin);


        TextButton easyButton = new TextButton("Easy", skin);
        TextButton mediumButton = new TextButton("Medium", skin);
        TextButton hardButton = new TextButton("Hard", skin);


        startButton.setDisabled(true);

        difficultyGroup = new ButtonGroup<>(easyButton,mediumButton,hardButton);
        difficultyGroup.setMaxCheckCount(1);
        difficultyGroup.setMinCheckCount(1);

        ClickListener buttonClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton selectedbutton = (TextButton) event.getListenerActor();

                resetButtonBorders();
                TextButton.TextButtonStyle newStyle = new TextButton.TextButtonStyle(selectedbutton.getStyle());
                newStyle.fontColor = Color.YELLOW;
                selectedbutton.setStyle(newStyle);

                startButton.setDisabled(false);
            }
        };

        easyButton.addListener(buttonClickListener);
        mediumButton.addListener(buttonClickListener);
        hardButton.addListener(buttonClickListener);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton selected = difficultyGroup.getChecked();
                if (selected != null) {
                    int difficulty = getDifficulty(selected);
                    startGame(difficulty);
                }
            }
        });


        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });



        Label title = new Label("Safari", skin);
        title.setFontScale(8f);
        table.row().pad(50);
        table.add(title).colspan(3);

        table.row().pad(20);
        table.add(startButton).padBottom(10).colspan(3);
        table.row().pad(10);
        table.add(quitButton).padBottom(50).colspan(3);

        table.row().pad(20);
        Label difficultyTitle = new Label("Difficulty", skin);
        table.add(difficultyTitle).colspan(3);

        table.row().pad(10);
        table.add(easyButton).padRight(10);
        table.add(mediumButton).padRight(10);
        table.add(hardButton);

        stage.addActor(table);
    }

    private void ShowBackGround()
    {
        stage = new Stage(new ScreenViewport());
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture("MainMenu/backgroundpixel.png");


        Gdx.input.setInputProcessor(stage);
    }


    private void resetButtonBorders() {
        for (TextButton button : difficultyGroup.getButtons()) {
            button.getStyle().fontColor = Color.WHITE;
        }
    }

    public int getDifficulty(TextButton button){
        if (button.getText().toString().equals("Easy")) return 1;
        if (button.getText().toString().equals("Medium")) return 2;
        return 3;
    }

    private void startGame(int diff) {
        System.out.println("Starting game with difficulty: " + diff);

        game.setScreen(new GameView(game, diff));
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        spriteBatch.dispose();
        backgroundTexture.dispose();
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

    public Stage getStage() {
        return stage;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
