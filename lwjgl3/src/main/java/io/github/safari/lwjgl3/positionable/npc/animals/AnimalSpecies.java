package io.github.safari.lwjgl3.positionable.npc.animals;

public enum AnimalSpecies {
    CAPYBARA(AnimalType.HERBIVORE, 5, 20, 100f),
    MAMMOTH(AnimalType.HERBIVORE, 20, 30, 100f),
    LION(AnimalType.PREDATOR, 20, 50, 100f),
    DINOSAUR(AnimalType.PREDATOR, 100, 40, 100f);

    private final AnimalType animalType;
    private final double maxAge;
    private final float speed;
    private final float visionRange;


    AnimalSpecies(AnimalType animalType, double maxAge, float speed, float visionRange) {
        this.animalType = animalType;
        this.maxAge = maxAge;
        this.speed = speed;
        this.visionRange = visionRange;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public double getMaxAge() {
        return maxAge;
    }

    public float getSpeed() {
        return speed;
    }

    public float getVisionRange() {
        return visionRange;
    }
}
