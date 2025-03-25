package io.github.safari.lwjgl3.positionable.objects;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class Bush extends Environment implements HerbivoreEdible, Positionable {
    public Bush(Position position) {
        super(position);
    }

}
