package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;

public class SleepAction extends Action implements CloneableAction {
    private float sleepTimer = 0;
    private float duration = 5;

    public SleepAction(float duration) {
        this.duration = duration;
    }

    public SleepAction() {
    }

    @Override
    public boolean act(float delta) {
        sleepTimer += delta * GamemodelInstance.gameModel.getTimeMultiplicator();
        return sleepTimer >= duration;
    }

    @Override
    public Action clone() {
        return new SleepAction();
    }
}
