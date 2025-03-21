package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.positionable.npc.animals.*;
import java.util.ArrayList;
import io.github.safari.lwjgl3.positionable.npc.animals.*;

public class Herd {
    private ArrayList<Animal> animals;
    private Animal Leader; //Szerintem kellene egy Leader allat amitol a tobbiek egy adott tavolsagon belul helyezkednek el.
    private boolean Herbivoregroup;

    public Herd(Animal chosenone, boolean isherbivore)
    {

        ArrayList<Animal> animals = new ArrayList<>();
        this.Leader = chosenone;
        animals.add(chosenone);
        this.Herbivoregroup = isherbivore;

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
