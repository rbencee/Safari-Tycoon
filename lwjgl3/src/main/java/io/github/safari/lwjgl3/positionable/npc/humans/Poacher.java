package io.github.safari.lwjgl3.positionable.npc.humans;

import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.npc.animals.*;

public class Poacher implements Moveable {
    private boolean isAlive;
    private int shootRange;
    private static int range;
    private int x;
    private int y;

    public Poacher() {
        this.isAlive = true;
    }

    public void KillAnimal(Animal target) {
    }

    public void KillRanger(Ranger target) {
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

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
}
