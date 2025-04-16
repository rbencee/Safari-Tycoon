package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;
import io.github.safari.lwjgl3.util.pathfinding.PathFinderHelper;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;

import java.time.LocalDateTime;
import java.util.*;

public class HerbivoreBehaviour implements Behaviour{
    private final HashMap<HerbivoreEdible, Position> knownFood = new HashMap<>();
    private final HashMap<Drinkable, Position> knownDrinkables = new HashMap<>();

    @Override
    public void createFittingAction(Animal animal) {
        Random rand = new Random();
        System.out.println(LocalDateTime.now());

        ArrayList<Position> obstacles = new ArrayList<>();
        for (Environment e : AnimalFactory.gameModel.getEnvironments()){
            obstacles.add(e.getPosition());
        }

        System.out.println("hunger: " + animal.getHunger() + " thirst: " + animal.getThirst());
        if (animal.getHunger() <= 30) {
            if (!knownFood.isEmpty()) {
                Vector2 start = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
                HerbivoreEdible nearestFood = getNearestFood(animal);
                Vector2 destination = new Vector2(nearestFood.getPosition().getX(), nearestFood.getPosition().getY());
                System.out.println("hunger before: " + animal.getHunger());
                addMoveToActions(animal, start, destination, obstacles);
                //todo ha odaért és már nincs ott növény, törli a knownból
                animal.addAction(Actions.after(new EatAction(animal)));
            }
        }
        else if (animal.getThirst() <= 30) {
            if (!knownDrinkables.isEmpty()) {
                Vector2 start = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
                Drinkable nearestWater = getNearestWater(animal);
                Vector2 destination = new Vector2(nearestWater.getPosition().getX(), nearestWater.getPosition().getY());
                addMoveToActions(animal, start, destination, obstacles);
                //todo ha odaért és már nincs ott tó, törli a knownból
                animal.addAction(Actions.after(new DrinkAction(animal)));
            }
        }
        else if (!animal.hasActions()) {
            int n = rand.nextInt(AnimalFactory.gameModel.getEnvironments().size());
            Environment e = AnimalFactory.gameModel.getEnvironments().get(n);
            Vector2 randomDestination = new Vector2(
                e.getPosition().getX(),
                e.getPosition().getY()
            );
            addMoveToActions(animal, new Vector2(animal.getPosition().getX(), animal.getPosition().getY()), randomDestination, obstacles);
        }
    }

    private static void addMoveToActions(Animal animal, Vector2 start, Vector2 destination, ArrayList<Position> obstacles) {
        PathFinderHelper pfh = new PathFinderHelper();
        List<Vector2> path = pfh.findRoute(start, destination, obstacles);

        for (Vector2 vector2 : path) {
            animal.addAction(Actions.after(Actions.moveTo(vector2.x, vector2.y, 5)));
        }
    }


    @Override
    public void detectFood(Animal animal, EdibleCollection foodPositions) {
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

    private HerbivoreEdible getNearestFood(Animal animal){
        HerbivoreEdible closest = null;
        float closestDist2 = Float.MAX_VALUE;

        for (HerbivoreEdible plant : knownFood.keySet()) {
            Position foodPos = plant.getPosition();
            float dist2 = Position.distance2(animal.getPosition(), foodPos);
            if (dist2 < closestDist2) {
                closestDist2 = dist2;
                closest = plant;
            }
        }
        return closest;
    }

    private Drinkable getNearestWater(Animal animal){
        Drinkable closest = null;
        float closestDist2 = Float.MAX_VALUE;

        for (Drinkable water : knownDrinkables.keySet()) {
            Position drinkPos = water.getPosition();
            float dist2 = Position.distance2(animal.getPosition(), drinkPos);
            if (dist2 < closestDist2) {
                closestDist2 = dist2;
                closest = water;
            }
        }
        return closest;
    }

}
