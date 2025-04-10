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
    private HashMap<HerbivoreEdible, Position> knownFood = new HashMap<>();
    private HashMap<Drinkable, Position> knownDrinkables = new HashMap<>();

    @Override
    public Action createFittingAction(Animal animal) {
        Random rand = new Random();
        if (knownFood.isEmpty()) {
            //todo a randomon lehetne szépíteni
            return Actions.moveBy(rand.nextFloat(-50, 50), rand.nextFloat(-50, 50), rand.nextFloat((float) (1/ animal.getSpeed()), (float) (140/animal.getSpeed())));
        }
        if (animal.getHunger() <= 30) {
            if (!knownFood.isEmpty()) {
                Map.Entry<HerbivoreEdible, Position> entry = knownFood.entrySet().iterator().next();
                Position pos = entry.getValue();
                float dist = Position.distance(pos, animal.getPosition());

                return Actions.moveTo(pos.getX(), pos.getY(), (float) (dist / animal.getSpeed()));//todo legközelebbihez menjen
            }
        }
        else if (animal.getThirst() <= 30) {
            if (!knownDrinkables.isEmpty()) {
                Map.Entry<Drinkable, Position> entry = knownDrinkables.entrySet().iterator().next();
                Position pos = entry.getValue();
                float dist = Position.distance(pos, animal.getPosition());

                return Actions.moveTo(pos.getX(), pos.getY(), (float) (dist / animal.getSpeed()));//todo legközelebbihez menjen
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
        //todo ha eladtunk vmit, azt ki kellene törölni
        Vector2 animalPos = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
        for (HerbivoreEdible plant : foodPositions.getAllHerbivoreEdible()) {
            if (knownFood.containsKey(plant)){
                if (knownFood.get(plant).equals(plant.getPosition())){
                    continue;
                }
            }

            Vector2 foodPos = new Vector2(plant.getPosition().getX(), plant.getPosition().getY());
            if(animalPos.dst(foodPos) <= animal.getVisionRange()) {
                knownFood.put(plant, plant.getPosition().clone());
            }
        }

        if (!knownFood.isEmpty()) {
            for (HerbivoreEdible d : knownFood.keySet()) {
                System.out.println(knownFood.get(d).getX() + " " + knownFood.get(d).getY());
            }
        }
    }

    @Override
    public void detectWater(Animal animal, EdibleCollection drinkPositions) {
        Vector2 animalPos = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
        for (Drinkable drink : drinkPositions.getAllDrinkable()) {
            if (knownDrinkables.containsKey(drink)){
                if (knownDrinkables.get(drink).equals(drink.getPosition())){
                    continue;
                }
            }

            Vector2 foodPos = new Vector2(drink.getPosition().getX(), drink.getPosition().getY());
            if(animalPos.dst(foodPos) <= animal.getVisionRange()) {
                knownDrinkables.put(drink, drink.getPosition().clone());
            }
        }

        if (!knownDrinkables.isEmpty()){
        for(Drinkable d : knownDrinkables.keySet()){
            System.out.println(knownDrinkables.get(d).getX() + " " + knownDrinkables.get(d).getY());
        }
        }
    }

    @Override
    public boolean shouldCreateNewAction(Animal animal) {
        return true; //animal.getHunger() < 30 || animal.getThirst() < 30; todo
    }
}
