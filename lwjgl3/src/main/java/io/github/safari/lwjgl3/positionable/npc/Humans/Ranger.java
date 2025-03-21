package io.github.safari.lwjgl3.positionable.npc.Humans;


import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;

import java.util.ArrayList;


public class Ranger extends Position implements Moveable {
    private boolean isAlive;
    private int x;
    private int y;
    private int shootRange;
    private static int range;
    private ArrayList<Poacher> knownPoachers;

    public Ranger(float x, float y,int width, int height) {
        super(x,y,width,height);
        this.isAlive = true;
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
}

