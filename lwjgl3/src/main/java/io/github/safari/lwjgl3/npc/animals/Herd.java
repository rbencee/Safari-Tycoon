package io.github.safari.lwjgl3.npc.animals;
import npc.animals.Animal;
import util.*;

import java.util.ArrayList;

public class Herd {
    private ArrayList<Animal> animals;
    private AnimalType animalType;

    public Herd(AnimalType animalType)
    {
        this.animalType = animalType;
        //type nem eleg a konstruktorhoz, tudnia kell hova kell raknia

    }

    public joinHerd(Animal join)
    {
        animals.add(join);

    }

    public ChooseOneObjective()
    {

    }

    public int animalcount()
    {
        return animals.size();
    }

    public AnimalType getAnimalType() {
        return animalType;
    }
}
