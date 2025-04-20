package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;

import java.util.Random;

public class CloneableMoveToAction extends MoveToAction implements CloneableAction {

    public CloneableMoveToAction(MoveToAction action) {
        Random random = new Random();
        this.setPosition(action.getX(), action.getY());
        this.setDuration(action.getDuration() + random.nextFloat((float) -5 / GamemodelInstance.gameModel.getTimeMultiplicator(), (float) 5 / GamemodelInstance.gameModel.getTimeMultiplicator()));
        this.setInterpolation(action.getInterpolation());
    }

    @Override
    public Action clone() {
        return new CloneableMoveToAction(this);
    }
}
