package io.github.safari.lwjgl3.positionable.objects;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class Environment implements Positionable {
    final Position position;

    public Environment(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
