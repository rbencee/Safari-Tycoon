package io.github.safari.lwjgl3.positionable.npc.Humans;

import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;


public class Poacher extends Position implements Moveable {
    private boolean isAlive;
    private int shootRange;
    private static int range;


    public Poacher(float x, float y,int width, int height) {
        super(x,y,width,height );
        this.isAlive = true;
    }

    public void KillAnimal(Animal target) {
    }

    public void KillRanger(Ranger target) {
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
