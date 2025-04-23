package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.util.Random;

public class CloneableMoveToAction extends MoveToAction implements CloneableAction {

    public CloneableMoveToAction(MoveToAction action) {
        Random random = new Random();
        this.setStartPosition(action.getStartX(), action.getStartY());
        this.setPosition(action.getX(), action.getY());
        this.setDuration(action.getDuration() + random.nextFloat(-0.1f, 0.1f));
        this.setInterpolation(action.getInterpolation());
    }

    @Override
    public Action clone() {
        return new CloneableMoveToAction(this);
    }
}
