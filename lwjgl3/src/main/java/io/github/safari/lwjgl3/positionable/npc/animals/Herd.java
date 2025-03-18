package lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.npc.animals;


import java.util.ArrayList;

public class Herd {
    private ArrayList<Animal> animals;
    private Animal animaltype;


    public Herd(Animal chosenone)
    {
        this.animaltype = chosenone;
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(chosenone);

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

    public Animal getAnimalType() {
        return animalType;
    }
}
