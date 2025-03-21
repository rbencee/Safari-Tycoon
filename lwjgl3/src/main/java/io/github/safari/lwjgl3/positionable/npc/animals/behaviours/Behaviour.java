package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;

public interface Behaviour {
    Action createFittingAction(Animal animal);
    boolean shouldCreateNewAction(Animal animal);
}
