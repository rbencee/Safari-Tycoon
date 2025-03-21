package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.Position;

import java.util.ArrayList;

public interface Animal {
    int getVisionRange();
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
    Position getPosition();
    ArrayList<Position> getKnownFood();
    ArrayList<Position> getKnownWater();
    AnimalType getAnimalType();
    AnimalSpecies getAnimalSpecies();
}
