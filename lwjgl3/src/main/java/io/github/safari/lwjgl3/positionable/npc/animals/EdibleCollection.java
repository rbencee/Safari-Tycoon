package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.objects.Drinkable;
import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;

import java.util.List;

public interface EdibleCollection {
    List<Animal> getAllHerbivores();
    List<HerbivoreEdible> getAllHerbivoreEdible();
    List<Drinkable> getAllDrinkable();
}
