package io.github.safari.lwjgl3.positionable.visitors;

import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.util.Positionable;

public class Jeep implements Positionable, Moveable {
    private Texture texture;
    private Position position;

    public Jeep(Position position)
    {
        this.position = position;
        this.texture = new Texture("textures/others/jeep.png");
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
    public int GetSpeed() { return 0;}

    @Override
    public void setSpeed(int speed) {

    }


}
