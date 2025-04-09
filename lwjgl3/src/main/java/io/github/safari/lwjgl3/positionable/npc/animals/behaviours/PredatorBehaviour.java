package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.EdibleCollection;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PredatorBehaviour implements Behaviour {

    HashMap<Animal, Position> preyPositions = new HashMap<>();
    List<Drinkable> knownDrinkables = new ArrayList<>();

    @Override
    public Action createFittingAction(Animal animal) {
        //todo ha éhes, felkeresi a knownfoodokat szép sorban
        // ha útközben talál kaját, eszik
        return null;
    }

    @Override
    public boolean shouldCreateNewAction(Animal animal) {
        return false;
    }

    @Override
    public void detectFood(Animal animal, EdibleCollection foodPositions) {
        Vector2 animalPos = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
        for (Animal a : foodPositions.getAllHerbivores()) {
            if (preyPositions.containsKey(a)){
                if (preyPositions.get(a).equals(a.getPosition())){
                    continue;
                }
            }

            Vector2 foodPos = new Vector2(a.getPosition().getX(), a.getPosition().getY());
            if(animalPos.dst(foodPos) <= animal.getVisionRange()) {
                preyPositions.put(a, a.getPosition().clone());
            }
        }
    }


    @Override
    public void detectWater(Animal animal, List<Drinkable> allDrinkable) {
        Vector2 animalPos = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
        for (Drinkable drink : allDrinkable) {
            if (knownDrinkables.contains(drink)){
                continue;
            }

            Vector2 foodPos = new Vector2(drink.getPosition().getX(), drink.getPosition().getY());
            if(animalPos.dst(foodPos) <= animal.getVisionRange()) {
                knownDrinkables.add(drink);
            }
        }
    }


}
