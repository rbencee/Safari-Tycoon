package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.Position;

public abstract class Animal extends Position {
    public static int VISION_RANGE;
    int age;
    int hunger;
    int thirst;
    //??? picture;
    boolean isAlive;
    Position position;
    Position knownFood;
    Position knownWater;
    int maxAge;
    private boolean isHerbivore;
}
