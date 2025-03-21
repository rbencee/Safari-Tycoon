package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.HerbivoreBehaviour;

public class AnimalFactory {
    public static Animal createCapybara(Position position){
        return new AnimalImpl(30, 1, 10, 100, 100, position, AnimalSpecies.CAPYBARA, new HerbivoreBehaviour());
    }
}
