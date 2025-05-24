package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalImplTest {

    @BeforeEach
    void setup() {
        GameModel mockGameModel = new GameModel(1) {
            @Override
            public int getTimeMultiplicator() {
                return 7;
            }
        };
        GamemodelInstance.gameModel = mockGameModel;
    }

    @Test
    void testAnimalAgesAndConsumesResources() {
        AnimalImpl animal = new AnimalImpl(
            0.0,
            100.0,
            100.0,
            new Position(0, 0, 1, 1),
            AnimalSpecies.LION
        );

        float delta = 1.0f;
        animal.act(delta);

        assertTrue(animal.getAge() > 0);
        assertTrue(animal.getHunger() < 100);
        assertTrue(animal.getThirst() < 100);
        assertFalse(animal.isToRemove());
    }

    @Test
    void testAnimalDiesFromOldAge() {
        AnimalImpl animal = new AnimalImpl(
            20000,
            100,
            100,
            new Position(0, 0, 1, 1),
            AnimalSpecies.LION
        );

        animal.act(1.0f);
        assertTrue(animal.isToRemove());
    }

    @Test
    void testAnimalDiesFromStarvation() {
        AnimalImpl animal = new AnimalImpl(
            1,
            0.0,
            100.0,
            new Position(0, 0, 1, 1),
            AnimalSpecies.LION
        );

        animal.act(1.0f);
        assertTrue(animal.isToRemove());
    }

    @Test
    void testAnimalDiesFromThirst() {
        AnimalImpl animal = new AnimalImpl(
            1,
            100.0,
            0.0,
            new Position(0, 0, 1, 1),
            AnimalSpecies.LION
        );

        animal.act(1.0f);
        assertTrue(animal.isToRemove());
    }

    @Test
    void testAnimalDoesNotDieWhenResourcesAvailable() {
        AnimalImpl animal = new AnimalImpl(
            1,
            100.0,
            100.0,
            new Position(0, 0, 1, 1),
            AnimalSpecies.LION
        );

        animal.act(1.0f);
        assertFalse(animal.isToRemove());
    }

    @Test
    void testAnimalProperties() {
        AnimalImpl animal = new AnimalImpl(
            5.0,
            75.0,
            80.0,
            new Position(10, 20, 2, 2),
            AnimalSpecies.LION
        );

        assertEquals(5.0, animal.getAge(), 0.001);
        assertEquals(75.0, animal.getHunger(), 0.001);
        assertEquals(80.0, animal.getThirst(), 0.001);
        assertEquals(AnimalSpecies.LION, animal.getAnimalSpecies());
        assertEquals(new Position(10, 20, 2, 2), animal.getPosition());
    }
}
