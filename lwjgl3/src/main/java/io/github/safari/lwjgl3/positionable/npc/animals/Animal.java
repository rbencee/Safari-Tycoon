package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.Position;

public abstract class Animal extends Position {
    public static int VISION_RANGE;
    int age;
    int hunger;
    int thirst;
    boolean isAlive;
    Position position;
    Position knownFood;
    Position knownWater;
    int maxAge;

    // Konstruktor
    public Animal(float x, float y, int width, int height) {
        super(x, y, width, height); // Mivel a Position a szülő osztály, az Animal konstruktorban is ezt inicializáljuk.
        this.position = new Position(x, y, width, height); // Beállítjuk a pozíciót.
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
