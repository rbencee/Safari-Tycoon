package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HerdTest {
    private Herd herd;

    @BeforeEach
    void setUp() {
        GamemodelInstance.gameModel = new GameModel(1);
        Position position = new Position(5, 5, 1, 1);
        AnimalImpl animal = (AnimalImpl) AnimalFactory.createNew(AnimalSpecies.CAPYBARA, position);
        herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        herd.addToHerd(animal);
    }

    @Test
    void testAddToHerd() {
        assertEquals(1, herd.getAnimals().size());
    }

    @Test
    void testAct() {
        AnimalImpl animal = (AnimalImpl) AnimalFactory.createNew(
            AnimalSpecies.CAPYBARA,
            new Position(200, 200, 32, 32)
        );
        herd.addToHerd(animal);

        herd.act(0.1f);

    }
}
