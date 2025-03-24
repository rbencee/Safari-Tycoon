package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;

import java.util.List;

public interface EdibleCollection {
    List<Animal> getAllAnimals();
    List<HerbivoreEdible> getAllHerbivoreEdible();
}
