package io.github.safari.lwjgl3.positionable.visitors;

import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Moveable;
import io.github.safari.lwjgl3.util.Positionable;

public class Tourist implements Positionable, Moveable {
    private Texture texture;
    private Position position;
    private boolean isShown;

    public Tourist(Position position)
    {
        this.position = position;
        this.texture = new Texture("textures/humans/tourist.png");
        this.isShown = true;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
