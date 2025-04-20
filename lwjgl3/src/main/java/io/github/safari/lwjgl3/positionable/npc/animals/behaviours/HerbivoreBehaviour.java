package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.EatAction;
import io.github.safari.lwjgl3.positionable.objects.HerbivoreEdible;

import java.util.HashMap;
import java.util.List;

public class HerbivoreBehaviour implements Behaviour {
    private final HashMap<HerbivoreEdible, Position> knownFood = new HashMap<>();

    @Override
    public boolean canCreateAction(Herd herd) {
        return herd.getHunger() <= 30 && !knownFood.isEmpty();
    }

    @Override
    public void doRepeatedly(Herd herd) {
        List<HerbivoreEdible> foodPositions = GamemodelInstance.gameModel.getAllHerbivoreEdible();
        for (HerbivoreEdible plant : foodPositions) {
            if (knownFood.containsKey(plant)) {
                if (knownFood.get(plant).equals(plant.getPosition())) {
                    continue;
                }
            }

            if (Position.distance(herd.getPosition(), plant.getPosition()) <= herd.getVisionRange()) {
                knownFood.put(plant, plant.getPosition().clone());
            }
        }
    }

    @Override
    public Array<Action> createActions(Herd herd) {
        Vector2 start = new Vector2(herd.getPosition().getX(), herd.getPosition().getY());
        HerbivoreEdible nearestFood = getNearestFood(herd);
        Vector2 destination = new Vector2(nearestFood.getPosition().getX(), nearestFood.getPosition().getY());
        Array<Action> actions = new Array<>(BehaviourHelper.createMoveToActions(herd, start, destination));
        actions.add(Actions.after(new EatAction(herd)));
        //todo ha odaért és már nincs ott növény, törli a knownból
        return actions;
    }


    private HerbivoreEdible getNearestFood(Herd herd) {
        HerbivoreEdible closest = null;
        float closestDist2 = Float.MAX_VALUE;

        for (HerbivoreEdible plant : knownFood.keySet()) {
            Position foodPos = plant.getPosition();
            float dist2 = Position.distance2(herd.getPosition(), foodPos);
            if (dist2 < closestDist2) {
                closestDist2 = dist2;
                closest = plant;
            }
        }
        return closest;
    }
}
