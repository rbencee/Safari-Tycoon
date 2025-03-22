package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ScorePanel {
    private Label scoreLabel;
    private GameModel gameModel;
    private Table table;

    public ScorePanel(Skin skin, Stage stage, GameModel gameModel) {
        this.gameModel = gameModel;

        table = new Table();
        table.setFillParent(true);
        table.left().top();


        scoreLabel = new Label("", skin);
        scoreLabel.setText(getScoreText());


        table.add(scoreLabel).pad(10);
        stage.addActor(table);
    }


    public void updateScore() {
        scoreLabel.setText(getScoreText());
    }


    private String getScoreText() {
        return "Days Passed: " + gameModel.getDayspassed()  +"  "+
            "Money: " + gameModel.getMoney() +"  "+
            "Income: " + gameModel.getIncome()  +"  "+
            "Tourists: " + gameModel.getTouristcount() +"  "+
            "Herbivores: " + gameModel.sumHerbivorous() +"  "+
            "Predators: " + gameModel.sumPredators();
    }
}
