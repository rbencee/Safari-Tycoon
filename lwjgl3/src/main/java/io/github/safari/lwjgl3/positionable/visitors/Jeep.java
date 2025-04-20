package io.github.safari.lwjgl3.positionable.visitors;

import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;

public class Jeep implements Positionable, Moveable {
    private Texture texture;
    private Position position;
    private boolean tostart; // true ha bejarathoz megy, false ha a kijarathoz
    private ArrayList<Tourist> tourists;

    public Jeep(Position position)
    {
        this.position = position;
        this.texture = new Texture("textures/others/jeep.png");
        this.tostart = true;
        this.tourists = new ArrayList<>();
    }

    public void moveTowards(Position targetPosition, float speed) {
        float dx = targetPosition.getX() - this.position.getX();
        float dy = targetPosition.getY() - this.position.getY();

        float length = (float)Math.sqrt(dx*dx + dy*dy);

        if (length != 0) {
            dx /= length;
            dy /= length;
        }
        this.position.setX(this.position.getX() + dx * speed);
        this.position.setY(this.position.getY() + dy * speed);
    }


    public boolean trytoaddtourist(Tourist tourist)
    {
        if(tourists.size() >= 4)
        {
            return false;
        } else
        {
            tourists.add(tourist);
            return true;
        }
    }

    public int Drop_Off_Tourists()
    {
        int to_drop = tourists.size();
        tourists.clear();
        return to_drop;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    public Texture getTexture() {
        return texture;
    }
    @Override
    public void move() {}

    @Override
    public int getSpeed() { return 0;}

    @Override
    public void setSpeed(int speed) {

    }

    public boolean isTostart() {
        return tostart;
    }

    public void setTostart(boolean tostart) {
        this.tostart = tostart;
    }
}
