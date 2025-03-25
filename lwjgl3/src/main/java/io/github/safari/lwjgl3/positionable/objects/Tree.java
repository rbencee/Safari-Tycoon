package io.github.safari.lwjgl3.positionable.objects;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class Tree extends Environment implements HerbivoreEdible, Positionable  {

    public Tree(Position position) {
        super(position);
    }
}
