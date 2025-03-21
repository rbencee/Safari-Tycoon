package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.Position;

public class Capybara extends Animal {

    public Capybara(int x, int y) {
        super(x, y);
        this.age = 0;
        this.hunger = 0;
        this.thirst = 0;
            this.isAlive = true;
            this.maxAge = 10;
    }
}
