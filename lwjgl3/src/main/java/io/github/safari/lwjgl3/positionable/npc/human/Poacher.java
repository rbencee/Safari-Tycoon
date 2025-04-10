package io.github.safari.lwjgl3.positionable.npc.human;

import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.util.Positionable;

public class Poacher implements Moveable, Positionable {
    private boolean isAlive;
    private int shootRange;
    private static int range;
    private Position position;

    public Poacher(Position position) {
        this.isAlive = true;
        this.position = position;
    }

    public void KillAnimal(Animal target) {
    }

    public void KillRanger(Ranger target) {
    }

    @Override
    public void move() {
    }

    @Override
    public int getSpeed() {
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
