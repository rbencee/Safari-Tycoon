package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;

public class HerbivoreBehaviour implements Behaviour{
    @Override
    public Action createFittingAction(Animal animal) {
        if (animal.getHunger() < 30 || animal.getThirst() < 30){
            //todo go to food/water (depending on which one is lower)
        }
        if (false) { // todo food is present
            //animal.getKnownFood().addFood(food)
        }
        if (false) { // todo lake present
            //animal.getKnownWater().addWater(water)
        }

        return null;
    }

    @Override
    public boolean shouldCreateNewAction(Animal animal) {
        return animal.getHunger() < 30 || animal.getThirst() < 30;
    }


}
