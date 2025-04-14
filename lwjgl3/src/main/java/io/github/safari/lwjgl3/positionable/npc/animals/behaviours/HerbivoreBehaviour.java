package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;
import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;

import java.util.*;

public class HerbivoreBehaviour implements Behaviour{
    private final HashMap<HerbivoreEdible, Position> knownFood = new HashMap<>();
    private final HashMap<Drinkable, Position> knownDrinkables = new HashMap<>();

    @Override
    public void createFittingAction(Animal animal) {
        Random rand = new Random();

        ArrayList<Position> obstacles = new ArrayList<>();
        for (Drinkable d : AnimalFactory.gameModel.getAllDrinkable()){
            obstacles.add(d.getPosition());
        }

        System.out.println("hunger: " + animal.getHunger() + " thirst: " + animal.getThirst());
        if (animal.getHunger() <= 30) {
            System.out.println("known food:");
            for (Position p : knownFood.values()){
                System.out.println(p.getX() + " " + p.getY());
            }
            if (!knownFood.isEmpty()) {
                Map.Entry<HerbivoreEdible, Position> entry = knownFood.entrySet().iterator().next();
                Vector2 start = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
                System.out.println("start position of animal: " + start);
                Vector2 destination = new Vector2(entry.getValue().getX(), entry.getValue().getY());
                System.out.println("destination position (food): " + destination);

                PathGraph pathGraph = new PathGraph();
                List<Vector2> path = pathGraph.findRoute(start, destination, obstacles);

                for (int i = 1; i < path.size(); i++){ //első (nulladik) az, ahol az állat jelenleg van, erre nincs szükségünk
                    animal.addAction(Actions.after(Actions.moveTo(path.get(i).x, path.get(i).y, 5)));
                }

            }
        }
        else if (animal.getThirst() <= 30) {
            if (!knownDrinkables.isEmpty()) {
                Map.Entry<Drinkable, Position> entry = knownDrinkables.entrySet().iterator().next();
                Position pos = entry.getValue();
                float dist = Position.distance(pos, animal.getPosition());

            }
        }
        if (!animal.hasActions()) {
            //todo itt se menjen bele akadályokba
            System.out.println("random action");
            animal.addAction(Actions.moveBy(rand.nextFloat(-50, 50), rand.nextFloat(-50, 50), (float) (100/animal.getSpeed())));
        }
    }


    @Override
    public void detectFood(Animal animal, EdibleCollection foodPositions) {
        //todo ha eladtunk vmit, azt ki kellene törölni
        for (HerbivoreEdible plant : foodPositions.getAllHerbivoreEdible()) {
            if (knownFood.containsKey(plant)){
                if (knownFood.get(plant).equals(plant.getPosition())){
                    continue;
                }
            }

            if(Position.distance(animal.getPosition(), plant.getPosition()) <= animal.getVisionRange()) {
                knownFood.put(plant, plant.getPosition().clone());
            }
        }
    }

    @Override
    public void detectWater(Animal animal, EdibleCollection drinkPositions) {
        for (Drinkable drink : drinkPositions.getAllDrinkable()) {
            if (knownDrinkables.containsKey(drink)){
                if (knownDrinkables.get(drink).equals(drink.getPosition())){
                    continue;
                }
            }

            if(Position.distance(animal.getPosition(), drink.getPosition()) <= animal.getVisionRange()) {
                knownDrinkables.put(drink, drink.getPosition().clone());
            }
        }
    }

}
