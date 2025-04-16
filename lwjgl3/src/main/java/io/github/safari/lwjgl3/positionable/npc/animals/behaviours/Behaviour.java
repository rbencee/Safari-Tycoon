package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.EdibleCollection;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;

import java.util.List;

public interface Behaviour {
    void createFittingAction(Animal animal);
    void detectFood(Animal animal);
    void detectWater(Animal animal);

}
