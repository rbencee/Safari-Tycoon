package io.github.safari.lwjgl3.positionable.npc.animals;

import java.util.ArrayList;

public class Herd {
    private ArrayList<AnimalImpl> animals;


    public Herd(AnimalImpl chosenone)
    {

        ArrayList<AnimalImpl> animals = new ArrayList<>();
        animals.add(chosenone);

    }

    public void joinHerd(AnimalImpl join)
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
