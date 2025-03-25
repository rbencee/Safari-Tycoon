package io.github.safari.lwjgl3.positionable.npc.human;


import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;


public class Ranger implements Moveable, Positionable {
    private boolean isAlive;
    private int shootRange;
    private static int range;
    private ArrayList<Poacher> knownPoachers;
    private Position position;

    public Ranger(Position position) {
        this.isAlive = true;
        this.position = position;
    }

    public void KillAnimal(Animal target) {
    }

    public void KillPoacher(Poacher target) {
    }

    @Override
    public void move() {
    }

    @Override
    public int GetSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(int speed) {
    }

    @Override
    public Position getPosition() {
        return null;
    }
}

