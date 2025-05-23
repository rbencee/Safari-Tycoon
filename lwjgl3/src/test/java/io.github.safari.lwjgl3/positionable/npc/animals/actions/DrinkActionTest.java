package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrinkActionTest {

    @Test
    void testActCallsDrink() {
        AnimalImpl animal = (AnimalImpl) AnimalFactory.createNew(
            AnimalSpecies.CAPYBARA,
            new Position(0, 0, 1, 1)
        );
        animal.drink(); // Set to 100 initially
        animal.act(1.0f); // Simulate time passing so thirst decreases
        double before = animal.getThirst();

        DrinkAction action = new DrinkAction();
        action.setActor(animal);
        boolean result = action.act(0.1f);
        double after = animal.getThirst();

        assertTrue(result);
        assertTrue(before < 100);
        assertEquals(100.0, after, 0.001);
    }

    @Test
    void testCloneCreatesNewInstance() {
        DrinkAction original = new DrinkAction();
        assertNotSame(original, original.clone());
        assertTrue(original.clone() instanceof DrinkAction);
    }
}
