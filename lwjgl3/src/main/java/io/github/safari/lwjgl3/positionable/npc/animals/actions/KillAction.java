package io.github.safari.lwjgl3.positionable.npc.animals.actions;


import com.badlogic.gdx.scenes.scene2d.Action;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;

public class KillAction extends Action implements CloneableAction {

    private final Herd herdToHunt;

    public KillAction(Herd herd) {
        this.herdToHunt = herd;
    }

    @Override
    public boolean act(float delta) {
        if (!herdToHunt.getAnimals().isEmpty()) {
            AnimalImpl animal = herdToHunt.getAnimals().get(0);
            animal.remove();

            herdToHunt.getAnimals().remove(animal);
            if (herdToHunt.getAnimals().isEmpty()) {
                herdToHunt.remove();
                GamemodelInstance.gameModel.getHerds().remove(herdToHunt);
                GamemodelInstance.gameModel.getAllHerbivores().remove(herdToHunt);
            }

        }
        if (getActor() instanceof AnimalImpl actor) {
            actor.eat();
        }
        return true;
    }

    @Override
    public Action clone() {
        return new KillAction(herdToHunt);
    }
}
