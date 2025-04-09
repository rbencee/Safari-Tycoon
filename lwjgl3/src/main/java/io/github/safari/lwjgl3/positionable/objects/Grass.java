package io.github.safari.lwjgl3.positionable.objects;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class Grass extends Environment implements Positionable, HerbivoreEdible {
    public Grass(Position position) {
        super(position);
    }

}
