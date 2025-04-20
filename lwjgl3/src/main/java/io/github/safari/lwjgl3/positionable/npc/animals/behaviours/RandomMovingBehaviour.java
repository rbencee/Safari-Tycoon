package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.objects.Environment;

import java.util.Random;

public class RandomMovingBehaviour implements Behaviour {
    @Override
    public boolean canCreateAction(Herd herd) {
        return !GamemodelInstance.gameModel.getEnvironments().isEmpty();
    }

    @Override
    public void doRepeatedly(Herd herd) {

    }

    @Override
    public Array<Action> createActions(Herd herd) {
        Random rand = new Random();
        int n = rand.nextInt(GamemodelInstance.gameModel.getEnvironments().size());
        Environment e = GamemodelInstance.gameModel.getEnvironments().get(n);
        Vector2 randomDestination = new Vector2(
            e.getPosition().getX(),
            e.getPosition().getY()
        );
        return BehaviourHelper.createMoveToActions(herd.getAnimalSpecies().getSpeed(), new Vector2(herd.getPosition().getX(), herd.getPosition().getY()), randomDestination);
    }
}
