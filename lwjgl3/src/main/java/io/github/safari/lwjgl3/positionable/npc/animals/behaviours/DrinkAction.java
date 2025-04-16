package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;

public class DrinkAction extends Action {
    private final Animal animal;

    public DrinkAction(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean act(float delta) {
        animal.drink();
        return true;
    }
}
