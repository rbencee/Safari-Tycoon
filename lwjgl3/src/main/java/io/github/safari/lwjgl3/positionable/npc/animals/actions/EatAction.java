package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;

public class EatAction extends Action {
    private final Herd herd;

    public EatAction(Herd herd) {
        this.herd = herd;
    }

    @Override
    public boolean act(float delta) {
        for (AnimalImpl a : herd.getAnimals()){
            a.eat();
        }
        return true;
    }
}
