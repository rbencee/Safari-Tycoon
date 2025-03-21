package io.github.safari.lwjgl3.positionable.npc.animals;

public enum AnimalSpecies {
    CAPYBARA(AnimalType.HERBIVORE),
    MAMMOTH(AnimalType.HERBIVORE),
    LION(AnimalType.PREDATOR),
    DINOSAUR(AnimalType.PREDATOR);

    private final AnimalType animalType;

    AnimalSpecies(AnimalType animalType) {
        this.animalType = animalType;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }
}
