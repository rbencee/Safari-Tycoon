package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;

public class EatAction extends Action implements CloneableAction {

    @Override
    public boolean act(float delta) {
        ((AnimalImpl) getActor()).eat();
        return true;
    }

    @Override
    public Action clone() {
        return new EatAction();
    }
}
