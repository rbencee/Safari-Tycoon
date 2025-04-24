package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.positionable.Position;

public class AnimalFactory {

    public static Animal createCapybara(Position position) {
        return new AnimalImpl(0, 100, 100, createTexture("textures/animals/capybara.png"), position, AnimalSpecies.CAPYBARA);
    }
    public static Animal createMammoth(Position position){
        return new AnimalImpl(0, 100, 100, createTexture("textures/animals/mammoth.png"), position, AnimalSpecies.MAMMOTH);
    }
    public static Animal createLion(Position position){
        return new AnimalImpl(0, 100, 100, createTexture("textures/animals/lion.png"), position, AnimalSpecies.LION);
    }
    public static Animal createDinosaur(Position position) {
        return new AnimalImpl(0, 100, 100, createTexture("textures/animals/dino.png"), position, AnimalSpecies.DINOSAUR);
    }

    private static Texture createTexture(String path) {
        return new Texture(path);
    }

}
