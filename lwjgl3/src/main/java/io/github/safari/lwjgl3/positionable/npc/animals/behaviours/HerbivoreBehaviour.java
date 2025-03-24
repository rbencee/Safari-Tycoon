package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;
import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HerbivoreBehaviour implements Behaviour{
    HashMap<HerbivoreEdible, Position> plantPositions = new HashMap<>();
    List<Drinkable> knownDrinkables = new ArrayList<>();

    @Override
    public Action createFittingAction(Animal animal) {
        if (animal.getHunger() < 30 || animal.getThirst() < 30){
            if (animal.getHunger() < animal.getThirst()) {
                animal.addAction(Actions.moveTo( //todo legközelebbihez menjen
                    animal.getKnownFood().get(0).getX(),
                    animal.getKnownFood().get(0).getY()));
            }

            else {
                //todo
            }
        }
        if (!animal.hasActions()){
            //todo random place
        }

        return null;
    }


    @Override
    public void detectFood(Animal animal, EdibleCollection foodPositions) {
        Vector2 animalPos = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
        for (HerbivoreEdible plant : foodPositions.getAllHerbivoreEdible()) {
            if (plantPositions.containsKey(plant)){
                if (plantPositions.get(plant).equals(plant.getPosition())){
                    continue;
                }
            }

            Vector2 foodPos = new Vector2(plant.getPosition().getX(), plant.getPosition().getY());
            if(animalPos.dst(foodPos) <= animal.getVisionRange()) {
                plantPositions.put(plant, plant.getPosition().clone());
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


    @Override
    public boolean shouldCreateNewAction(Animal animal) {
        return animal.getHunger() < 30 || animal.getThirst() < 30;
    }


}
