package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;

public class EatAction extends CloneableAction {
    private final Herd herd;

    public EatAction(Herd herd) {
        this.herd = herd;
    }

    @Override
    public boolean act(float delta) {
        ((AnimalImpl) getActor()).eat();
        return true;
    }

    @Override
    public CloneableAction clone(CloneableAction action) {
        if (action instanceof EatAction) {

        }
        return null;
    }
}
