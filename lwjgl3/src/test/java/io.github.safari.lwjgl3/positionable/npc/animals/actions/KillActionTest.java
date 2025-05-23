package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KillActionTest {

    @BeforeEach
    void resetGameModel() {
        GamemodelInstance.gameModel.getHerds().clear();
        GamemodelInstance.gameModel.getAllHerbivores().clear();
    }

    @Test
    void testActKillsAnimalAndRemovesEmptyHerd() {
        AnimalImpl prey = (AnimalImpl) AnimalFactory.createNew(
            AnimalSpecies.CAPYBARA,
            new Position(0, 0, 1, 1)
        );
        Herd herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        herd.addToHerd(prey);

        GamemodelInstance.gameModel.getHerds().add(herd);
        GamemodelInstance.gameModel.getAllHerbivores().add(herd);

        AnimalImpl predator = (AnimalImpl) AnimalFactory.createNew(
            AnimalSpecies.LION,
            new Position(1, 1, 1, 1)
        );

        KillAction action = new KillAction(herd);
        action.setActor(predator);

        boolean result = action.act(0.1f);

        assertTrue(result);
        assertTrue(prey.isToRemove());
        assertFalse(GamemodelInstance.gameModel.getHerds().contains(herd));
        assertFalse(GamemodelInstance.gameModel.getAllHerbivores().contains(herd));
    }

    @Test
    void testCloneCreatesNewInstanceWithSameHerdReference() {
        Herd herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        KillAction original = new KillAction(herd);
        KillAction copy = (KillAction) original.clone();

        assertNotSame(original, copy);
        assertSame(original.herdToHunt, copy.herdToHunt);
    }
}
