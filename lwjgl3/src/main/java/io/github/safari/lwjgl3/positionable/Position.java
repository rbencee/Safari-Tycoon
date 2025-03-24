package io.github.safari.lwjgl3.positionable;

import com.badlogic.gdx.Gdx;

public class Position {

    private int x;
    private int y;

    public Position() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Position clone(){
        return new Position(x,y);
    }
}
