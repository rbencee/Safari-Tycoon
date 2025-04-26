package io.github.safari.lwjgl3.positionable;


import com.badlogic.gdx.math.Vector2;

public class Position {

    private float x;
    private float y;
    private int width;
    private int height;

    public Position(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }


    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {return width;}

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Position clone(){
        return new Position(x,y,width,height);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Position other = (Position) obj;
        return other.getX() == this.getX() &&
            other.getY() == this.getY() &&
            other.getHeight() == this.getHeight() &&
            other.getWidth() == this.getWidth();
    }

    public static float distance(Position pos1, Position pos2){
        //normal distance
        return new Vector2(pos1.getX(), pos1.getY()).dst(new Vector2(pos2.getX(), pos2.getY()));
    }

    public static float distance2(Position pos1, Position pos2) {
        //squared distance (faster to compute because of no root square)
        return new Vector2(pos1.getX(), pos1.getY()).dst2(new Vector2(pos2.getX(), pos2.getY()));
    }

    @Override
    public String toString() {
        return "Position{" +
            "y=" + y +
            ", x=" + x +
            '}';
    }
}
