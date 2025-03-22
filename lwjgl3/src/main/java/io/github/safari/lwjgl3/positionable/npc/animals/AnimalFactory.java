package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.HerbivoreBehaviour;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.PredatorBehaviour;

public class AnimalFactory {
    public static Animal createCapybara(Position position){
        return new AnimalImpl(30, 0, 10, 100, 100, position, AnimalSpecies.CAPYBARA, new HerbivoreBehaviour());
    }
    public static Animal createMammoth(Position position){
        return new AnimalImpl(30, 0, 30, 100, 100, position, AnimalSpecies.MAMMOTH, new HerbivoreBehaviour());
    }
    public static Animal createLion(Position position){
        return new AnimalImpl(30, 0, 10, 100, 100, position, AnimalSpecies.LION, new PredatorBehaviour());
    }
    public static Animal createDinosaur(Position position){
        return new AnimalImpl(30, 0, 10, 100, 100, position, AnimalSpecies.DINOSAUR, new PredatorBehaviour());
    }
}
