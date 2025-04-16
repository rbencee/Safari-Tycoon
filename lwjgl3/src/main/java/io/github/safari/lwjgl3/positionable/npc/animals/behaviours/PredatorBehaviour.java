package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.EdibleCollection;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;
import io.github.safari.lwjgl3.util.pathfinding.PathFinderHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PredatorBehaviour implements Behaviour {

    HashMap<Animal, Position> preyPositions = new HashMap<>();
    HashMap<Drinkable, Position> knownDrinkables = new HashMap<>();

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
            if (!preyPositions.isEmpty()) {
                Vector2 start = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
                Animal nearestFood = getNearestFood(animal);
                Vector2 destination = new Vector2(nearestFood.getPosition().getX(), nearestFood.getPosition().getY());
                addMoveToActions(animal, start, destination, obstacles);
                //todo ha odaért és már nincs ott, törli + vhogy keres másikat
            }
        }
        else if (animal.getThirst() <= 30) {
            if (!knownDrinkables.isEmpty()) {
                Vector2 start = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
                Drinkable nearestWater = getNearestWater(animal);
                Vector2 destination = new Vector2(nearestWater.getPosition().getX(), nearestWater.getPosition().getY());
                addMoveToActions(animal, start, destination, obstacles);
                //todo ha odaért és már nincs ott a tó, törli a knownból
            }
        }
        else if (!animal.hasActions()) {
            int n = rand.nextInt(AnimalFactory.gameModel.getEnvironments().size());
            Environment e = AnimalFactory.gameModel.getEnvironments().get(n);
            Vector2 randomDestination = new Vector2(
                e.getPosition().getX(),
                e.getPosition().getY()
            );
            System.out.println("random dest: " + randomDestination);
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
        for (Animal prey : foodPositions.getAllHerbivores()) {
            if (preyPositions.containsKey(prey)){
                if (preyPositions.get(prey).equals(prey.getPosition())){
                    continue;
                }
            }
            if(Position.distance(animal.getPosition(), prey.getPosition()) <= animal.getVisionRange()) {
                preyPositions.put(prey, prey.getPosition().clone());
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

    private Animal getNearestFood(Animal animal){
        Animal closest = null;
        float closestDist2 = Float.MAX_VALUE;

        for (Animal prey : preyPositions.keySet()) {
            Position foodPos = prey.getPosition();
            float dist2 = Position.distance2(animal.getPosition(), foodPos);
            if (dist2 < closestDist2) {
                closestDist2 = dist2;
                closest = prey;
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
