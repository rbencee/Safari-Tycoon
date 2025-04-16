package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;

public class EatAction extends Action {
    private final Animal animal;

    public EatAction(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean act(float delta) {
        animal.eat();
        return true;
    }
}
