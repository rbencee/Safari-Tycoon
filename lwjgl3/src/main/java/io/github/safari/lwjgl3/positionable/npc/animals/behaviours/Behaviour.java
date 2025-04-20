package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;

import java.util.ArrayList;

public interface Behaviour{
    boolean canCreateAction(Herd herd);
    void doRepeatedly(Herd herd);
    Array<Action> createActions(Herd herd);



    static ArrayList<Behaviour> createHerbivoreBehaviours(){
        ArrayList<Behaviour> behaviours = new ArrayList<>();
        behaviours.add(new HerbivoreBehaviour());
        behaviours.add(new DrinkingBehaviour());
        behaviours.add(new RandomMovingBehaviour());
        return behaviours;
    }
    static ArrayList<Behaviour> createPredatorBehaviours(){
        ArrayList<Behaviour> behaviours = new ArrayList<>();
        behaviours.add(new PredatorBehaviour());
        behaviours.add(new DrinkingBehaviour());
        behaviours.add(new RandomMovingBehaviour());
        return behaviours;
    }

}
