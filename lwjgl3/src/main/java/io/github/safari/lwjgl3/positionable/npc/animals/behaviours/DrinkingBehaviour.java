package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.DrinkAction;
import io.github.safari.lwjgl3.positionable.objects.Drinkable;

import java.util.HashMap;
import java.util.List;

public class DrinkingBehaviour implements Behaviour {
    private final HashMap<Drinkable, Position> knownDrinkables = new HashMap<>();

    @Override
    public boolean canCreateAction(Herd herd) {
        return !knownDrinkables.isEmpty() && herd.getThirst() <= 30;
    }

    @Override
    public void doRepeatedly(Herd herd) {
        List<Drinkable> drinkPositions = GamemodelInstance.gameModel.getAllDrinkable();
        for (Drinkable drink : drinkPositions) {
            if (knownDrinkables.containsKey(drink)) {
                if (knownDrinkables.get(drink).equals(drink.getPosition())) {
                    continue;
                }
            }
            if (Position.distance(herd.getPosition(), drink.getPosition()) <= herd.getVisionRange()) {
                knownDrinkables.put(drink, drink.getPosition().clone());
            }
        }

    }

    @Override
    public Array<Action> createActions(Herd herd) {
        Vector2 start = new Vector2(herd.getPosition().getX(), herd.getPosition().getY());
        Drinkable nearestWater = getNearestWater(herd);
        Vector2 destination = new Vector2(nearestWater.getPosition().getX(), nearestWater.getPosition().getY());
        Array<Action> actions = new Array<>(BehaviourHelper.addMoveToActions(herd, start, destination));
        //todo ha odaért és már nincs ott tó, törli a knownból
        actions.add(Actions.after(new DrinkAction(herd)));
        return actions;
    }


    private Drinkable getNearestWater(Herd herd) {
        Drinkable closest = null;
        float closestDist2 = Float.MAX_VALUE;

        for (Drinkable water : knownDrinkables.keySet()) {
            Position drinkPos = water.getPosition();
            float dist2 = Position.distance2(herd.getPosition(), drinkPos);
            if (dist2 < closestDist2) {
                closestDist2 = dist2;
                closest = water;
            }
        }
        return closest;
    }


}
