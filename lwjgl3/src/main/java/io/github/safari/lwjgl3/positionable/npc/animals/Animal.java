package io.github.safari.lwjgl3.npc.animals;

import io.github.safari.lwjgl3.util.*;

public abstract class Animal implements Positionable {
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
