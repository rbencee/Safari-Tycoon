package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;

public class DrinkAction extends Action implements CloneableAction {
    @Override
    public boolean act(float delta) {
        ((AnimalImpl) getActor()).drink();
        return true;
    }

    @Override
    public Action clone() {
        return new DrinkAction();
    }
}
