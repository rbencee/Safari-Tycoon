package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;

public class PredatorBehaviour implements Behaviour {
    @Override
    public Action createFittingAction(Animal animal) {
        return null;
    }

    @Override
    public boolean shouldCreateNewAction(Animal animal) {
        return false;
    }
}
