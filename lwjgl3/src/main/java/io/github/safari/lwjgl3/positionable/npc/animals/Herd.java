package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;

public class Herd extends Group implements Positionable {
    private final AnimalSpecies animalSpecies;
    private final ArrayList<AnimalImpl> animals = new ArrayList<>();
    private final ArrayList<Behaviour> behaviours;


    //todo ha collide-ol egy másik ugyanolyan típusú herddel, összeolvadnak


    public Herd(AnimalSpecies animalSpecies, ArrayList<Behaviour> behaviours) {
        this.animalSpecies = animalSpecies;
        this.behaviours = behaviours;
    }

    public void addToHerd(AnimalImpl animal) {
        animals.add(animal);
        addActor(animal);
    }

    public void ChooseOneObjective() {

    }

    public ArrayList<AnimalImpl> getAnimals() {
        return animals;
    }

    public AnimalType getAnimalType() {
        return animalSpecies.getAnimalType();
    }

    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
    }

    public int animalCount() {
        return animals.size();
    }

    @Override
    public Position getPosition() {
        return animals.get(0).getPosition();
    }

    public double getHunger() {
        return animals.get(0).getHunger();
    }

    public double getThirst() {
        return animals.get(0).getThirst();
    }

    public float getVisionRange() {
        return animals.get(0).getVisionRange();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (animals.isEmpty()) return;

        ArrayList<Herd> herdsToRemove = new ArrayList<>();
        for (Herd herd : GamemodelInstance.gameModel.getHerds()) {
            if (herd.equals(this) || herdsToRemove.contains(herd)) continue;


            if (herd.getAnimalSpecies().equals(animalSpecies)) {
                if (Position.distance(getPosition(), herd.getPosition()) <= animals.get(0).getVisionRange()) {
                    herdsToRemove.add(herd);

                    for (AnimalImpl a : herd.getAnimals()) {
                        addToHerd(a);
                    }
                    herd.getAnimals().clear();

                    herd.remove();

                }
            }
        }
        GamemodelInstance.gameModel.getHerds().removeAll(herdsToRemove);

        for (Behaviour behaviour : behaviours) {
            behaviour.doRepeatedly(this);

            if (hasActions()) {
                return;
            }

            if (behaviour.canCreateAction(this)) {
                Array<Action> actions = behaviour.createActions(this);
                for (Action a : actions) {
                    addAction(a);

                }
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
