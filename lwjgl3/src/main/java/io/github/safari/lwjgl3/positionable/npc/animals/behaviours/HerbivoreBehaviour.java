package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;
import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;

import java.util.*;

public class HerbivoreBehaviour implements Behaviour{
    HashMap<HerbivoreEdible, Position> plantPositions = new HashMap<>();
    List<Drinkable> knownDrinkables = new ArrayList<>();

    @Override
    public Action createFittingAction(Animal animal) {
        Random rand = new Random();
        if (plantPositions.isEmpty()) {
            System.out.println("plantpositions empty");
            //todo a randomon lehetne szépíteni
            return Actions.moveBy(rand.nextFloat(-50, 50), rand.nextFloat(-50, 50), rand.nextFloat((float) (1/ animal.getSpeed()), (float) (140/animal.getSpeed())));
        }
        if (animal.getHunger() < 100 || animal.getThirst() < 100) {
            System.out.println("hungry");
            if (!plantPositions.isEmpty()) {
                Map.Entry<HerbivoreEdible, Position> entry = plantPositions.entrySet().iterator().next();
                Position pos = entry.getValue();
                Float dist = Position.distance(pos, animal.getPosition());

                System.out.println("going to: " + pos.getX() + " " + pos.getY());
                return Actions.moveTo(pos.getX(), pos.getY(), (float) (dist/animal.getSpeed()));//todo legközelebbihez menjen
            } else {
                //todo
            }
        }
        if (!animal.hasActions()) {
            return Actions.moveBy(-100, 100, 10);
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
                System.out.println("foodpos position:" + foodPos);
                plantPositions.put(plant, plant.getPosition());
            }
        }
        if (!plantPositions.isEmpty()) {
            if (plantPositions.get(0) != null)
            System.out.println("first saved food position: " + plantPositions.get(0).getX() + " " + plantPositions.get(0).getY());
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
        return true; //animal.getHunger() < 30 || animal.getThirst() < 30; todo
    }


}
