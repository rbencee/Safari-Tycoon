package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.npc.animals.*;
import java.util.ArrayList;
import io.github.safari.lwjgl3.positionable.npc.animals.*;

public class Herd {
    private ArrayList<Animal> animals;


    public Herd(Animal chosenone)
    {

        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(chosenone);

    }

    public void joinHerd(Animal join)
    {
        animals.add(join);

    }

    public void ChooseOneObjective()
    {

    }

    public boolean getIsherbivore()
    {
        return true;
    }

    public int animalcount()
    {
        return animals.size();
    }

    //public Animal getAnimalType() {
       // return animalType;
    //}
}
