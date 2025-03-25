package io.github.safari.lwjgl3.positionable.objects;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class Lake extends Environment implements Positionable, Drinkable{
    public Lake(Position position) {
        super(position);
    }

}
