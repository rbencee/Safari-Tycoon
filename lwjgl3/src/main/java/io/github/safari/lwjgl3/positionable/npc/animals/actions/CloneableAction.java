package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

public abstract class CloneableAction extends Action {
    public abstract CloneableAction clone(CloneableAction action);
}
