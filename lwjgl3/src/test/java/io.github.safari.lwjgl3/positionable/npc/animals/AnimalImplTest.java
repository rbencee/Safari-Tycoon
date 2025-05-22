package io.github.safari.lwjgl3.positionable.npc.animals;

import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.SpeciesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnimalImplTest {

    @BeforeEach
    void setup() {
        GameModel mockGameModel = new GameModel(1){
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
            animalMaxAge(AnimalSpecies.LION) + 1,
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

    private double animalMaxAge(AnimalSpecies species) {
        return SpeciesFactory.getSpeciesData(species).maxAge();
    }
}
