package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.EatAction;

import java.util.HashMap;
import java.util.List;

import static io.github.safari.lwjgl3.positionable.npc.animals.behaviours.BehaviourHelper.createMoveToActions;

public class PredatorBehaviour implements Behaviour {

    HashMap<Herd, Position> preyPositions = new HashMap<>();


    private Herd getNearestFood(Herd Herd) {
        Herd closest = null;
        float closestDist2 = Float.MAX_VALUE;

        for (Herd prey : preyPositions.keySet()) {
            Position foodPos = prey.getPosition();
            float dist2 = Position.distance2(Herd.getPosition(), foodPos);
            if (dist2 < closestDist2) {
                closestDist2 = dist2;
                closest = prey;
            }
        }
        return closest;
    }


    @Override
    public boolean canCreateAction(Herd herd) {
        return herd.getHunger() <= 30 && !preyPositions.isEmpty();
    }

    @Override
    public void doRepeatedly(Herd herd) {
        List<Herd> preys = GamemodelInstance.gameModel.getAllHerbivores();
        for (Herd prey : preys) {
            if (preyPositions.containsKey(prey)) {
                if (preyPositions.get(prey).equals(prey.getPosition())) {
                    continue;
                }
            }
            if (Position.distance(herd.getPosition(), prey.getPosition()) <= herd.getVisionRange()) {
                preyPositions.put(prey, prey.getPosition().clone());
            }
        }
    }

    @Override
    public Array<Action> createActions(Herd herd) {
        Vector2 start = new Vector2(herd.getPosition().getX(), herd.getPosition().getY());
        Herd nearestFood = getNearestFood(herd);
        Vector2 destination = new Vector2(nearestFood.getPosition().getX(), nearestFood.getPosition().getY());
        Array<Action> actions = createMoveToActions(herd.getAnimalSpecies().getSpeed(), start, destination);
        //todo ha odaért és már nincs ott, törli + vhogy keres másikat
        actions.add(Actions.after(new EatAction(herd)));
        return actions;
    }
}
