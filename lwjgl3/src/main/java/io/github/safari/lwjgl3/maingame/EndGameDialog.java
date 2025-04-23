package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.safari.lwjgl3.maingame.GameModel;

public class EndGameDialog extends Dialog {
    public EndGameDialog(boolean isVictory, GameModel model, Skin skin, Stage stage) {
        super(isVictory ? "Congratulations!" : "Game Over", skin);

        String resultText = isVictory ? "Win!" : "Lose!";
        String stats =
            "Days: " + model.getDayspassed() + "\n" +
                "Money: " + model.getMoney() + "\n" +
                "Tourists: " + model.getTouristcount() + "\n" +
                "Herbivores: " + model.sumHerbivores() + "\n" +
                "Predatores: " + model.sumPredators();


        text(resultText + "\n\n" + stats);

        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.getLabel().setFontScale(1.2f); // opcionális: nagyobb betű
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quitButton.getLabel().setColor(Color.YELLOW); // kijelölés-szerű vizuális effekt
                Gdx.app.exit();
            }
        });

        getButtonTable().add(quitButton).pad(10).width(200).height(80);

        show(stage);
        toFront();
    }
}
