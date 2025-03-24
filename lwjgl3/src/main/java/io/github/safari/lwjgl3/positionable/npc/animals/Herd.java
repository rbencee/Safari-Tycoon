package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.scenes.scene2d.Group;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;

public class Herd extends Group implements Positionable {
    private final AnimalSpecies animalSpecies;
    private final ArrayList<AnimalImpl> animals = new ArrayList<>();

    //todo ha collide-ol egy másik ugyanolyan típusú herddel, összeolvadnak


    public Herd(AnimalSpecies animalSpecies) {
        this.animalSpecies = animalSpecies;
    }

    public void addToHerd(AnimalImpl animal) {
        animals.add(animal);
    }

    public void ChooseOneObjective() {

    }

    public AnimalType getAnimalType(){
        return animalSpecies.getAnimalType();
    }

    public AnimalSpecies getAnimalSpecies(){
        return animalSpecies;
    }

    public int animalcount() {
        return animals.size();
    }

    @Override
    public Position getPosition() {
        return null;
    }
}
