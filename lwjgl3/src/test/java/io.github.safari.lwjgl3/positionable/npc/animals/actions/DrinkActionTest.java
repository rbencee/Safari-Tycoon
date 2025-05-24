package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrinkActionTest {

    @BeforeEach
    void setup() {
        GameModel mockGameModel = new GameModel(1) {
            @Override
            public int getTimeMultiplicator() {
                return 1;
            }
        };
        GamemodelInstance.gameModel = mockGameModel;
    }

    @Test
    void testActCallsDrink() {
        Animal animal = AnimalFactory.createNew(
            AnimalSpecies.CAPYBARA,
            new Position(0, 0, 1, 1)
        );
        AnimalImpl animal1 = (AnimalImpl) animal;
        animal1.drink();
        animal1.act(1.0f);

        double before = animal.getThirst();
        DrinkAction action = new DrinkAction();
        action.setActor(animal1);

        boolean result = action.act(0.1f);
        double after = animal.getThirst();

        assertTrue(result);
        assertTrue(before < 100);
        assertEquals(100.0, after, 0.001);
    }

    @Test
    void testCloneCreatesNewInstance() {
        DrinkAction original = new DrinkAction();
        DrinkAction cloned = (DrinkAction) original.clone();

        assertNotSame(original, cloned);
        assertNotNull(cloned);
    }
}
