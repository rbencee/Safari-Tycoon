package io.github.safari.lwjgl3.positionable.npc.animals;

public enum AnimalSpecies {
    CAPYBARA(AnimalType.HERBIVORE, 100, 10, 300f, 4),
    MAMMOTH(AnimalType.HERBIVORE, 300, 15, 200f, 7),
    LION(AnimalType.PREDATOR, 250, 30, 100f, 8),
    DINOSAUR(AnimalType.PREDATOR, 300, 20, 300f, 11);

    private final AnimalType animalType;
    private final double maxAge;
    private final float speed;
    private final float visionRange;
    private final int reproductionTime;


    AnimalSpecies(AnimalType animalType, double maxAge, float speed, float visionRange, int reproductionTime) {
        this.animalType = animalType;
        this.maxAge = maxAge;
        this.speed = speed;
        this.visionRange = visionRange;
        this.reproductionTime = reproductionTime;
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

    public float getReproductionTime() {
        return (float) reproductionTime;
    }
}
