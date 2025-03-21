package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;

import java.util.ArrayList;

class AnimalImpl extends Actor implements Animal {
    public int visionRange;

    double age;
    double maxAge;
    double hunger;
    double thirst;
    //??? picture;
    Position position;
    ArrayList<Position> knownFood;
    ArrayList<Position> knownWater;
    AnimalSpecies animalSpecies;
    final Behaviour behaviour;

    public AnimalImpl (
        int visionRange,
        double age,
        double maxAge,
        double hunger,
        double thirst,
        Position position,
        AnimalSpecies animalSpecies, Behaviour behaviour) {

        this.visionRange = visionRange;
        this.age = age;
        this.maxAge = maxAge;
        this.hunger = hunger;
        this.thirst = thirst;
        this.position = position;
        this.animalSpecies = animalSpecies;
        this.behaviour = behaviour;
    }

    @Override
    public int getVisionRange() {
        return visionRange;
    }

    @Override
    public double getAge() {
        return age;
    }

    @Override
    public double getMaxAge() {
        return maxAge;
    }

    @Override
    public double getHunger() {
        return hunger;
    }

    @Override
    public double getThirst() {
        return thirst;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public ArrayList<Position> getKnownFood() {
        return knownFood;
    }

    @Override
    public ArrayList<Position> getKnownWater() {
        return knownWater;
    }

    @Override
    public AnimalType getAnimalType() {
        return animalSpecies.getAnimalType();
    }

    @Override
    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(!this.hasActions() || behaviour.shouldCreateNewAction(this)){
            this.clearActions();
            this.addAction(behaviour.createFittingAction(this));
        }
    }
}
