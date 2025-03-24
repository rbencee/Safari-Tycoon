package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;

public interface Animal {
    float getVisionRange();
    double getAge();
    double getMaxAge();

    /**
     * max is 100, dies at 0
     * @return
     */
    double getHunger();

    /**
     * max is 100, dies at 0
     * @return
     */
    double getThirst();
    double getSpeed();
    Position getPosition();
    ArrayList<Position> getKnownFood();
    ArrayList<Position> getKnownWater();
    AnimalType getAnimalType();
    AnimalSpecies getAnimalSpecies();
    void addAction(Action action);
    Array<Action> getActions();
    boolean hasActions();
}
