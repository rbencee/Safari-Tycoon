package io.github.safari.lwjgl3.positionable.npc.animals.actions;


import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;

public class Killaction extends Action implements CloneableAction {

    private final Herd herd;

    public Killaction(Herd herd) {
        this.herd = herd;
    }

    @Override
    public boolean act(float delta) {
        if (!herd.getAnimals().isEmpty()) {
            AnimalImpl animal = herd.getAnimals().get(0);
            animal.remove();

            herd.getAnimals().remove(animal);
            if (herd.getAnimals().isEmpty()) {
                herd.remove();
                GamemodelInstance.gameModel.getHerds().remove(herd);
                GamemodelInstance.gameModel.getAllHerbivores().remove(herd);
            }

        }
        if (getActor() instanceof AnimalImpl actor) {
            actor.eat();
        }
        return true;
    }

    @Override
    public Action clone() {
        return new Killaction(herd);
    }
}
