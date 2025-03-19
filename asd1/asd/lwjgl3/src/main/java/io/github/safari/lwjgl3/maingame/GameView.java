package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameView implements Screen {

    private Game game;
    private Skin skin;
    private Stage stage;
    GameModel gameModel;
    MainMenu mainMenu;
    private Timer timer;


    public GameView(int difficulty)
    {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        com.badlogic.gdx.scenes.scene2d.ui.Label title = new Label("Safari", skin);
        table.add(title);



        this.gameModel = new GameModel(difficulty);


    }

    public void UpdateScore()
    {

        int time = gameModel.getDayspassed();
        int income = gameModel.getIncome();
        int balance = gameModel.getMoney();
        int touristcount = gameModel.getTouristcount();
        int herbivorecount = gameModel.sumHerbivorous();
        int sumpredators = gameModel.sumPredators();

    }

    private void StartNewGame(){
        stop();

        gameModel.InitializeGame();

        timer = new Timer(10, oneGameCycleAction);
        timer.start();



    }





    private void stop(){
        if(timer != null && timer.isRunning()){
            timer.stop();
        }
    }

    private final ActionListener oneGameCycleAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){

        }
    };
    @Override
    public void show()
    {


    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

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

    @Override
    public void dispose() {

    }
}
