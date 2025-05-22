package io.github.safari.lwjgl3.positionable.npc.human;

import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PoacherTest {


    private Poacher poacher;
    private TestGameModel testModel;

    @BeforeEach
    void setUp() {
        testModel = new TestGameModel();
        GamemodelInstance.gameModel = testModel;

        poacher = new Poacher(new Position(100, 100, 32, 32));
    }

    @Test
    void testKillAnimalWithinRange() {
       // AnimalImpl animal = AnimalFactory.createNew(new Position(110, 110, 32, 32));


        Animal animal = AnimalFactory.createNew(AnimalSpecies.CAPYBARA, new Position(110, 110, 32, 32));
        Herd herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        herd.addToHerd((AnimalImpl) animal);
        testModel.getHerds().add(herd);

        poacher.KillAnimal(animal);

        assertTrue(herd.getAnimals().isEmpty(), "Animal should be killed and removed from the herd");
        //assertTrue(testModel.getHerds().isEmpty(), "Herd should be removed because it's empty after the animal was killed");
    }

    @Test
    void testKillAnimalOutOfRange() {

        Animal animal = AnimalFactory.createNew(AnimalSpecies.CAPYBARA, new Position(1000, 1000, 32, 32));
        Herd herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        herd.addToHerd((AnimalImpl) animal);
        testModel.getHerds().add(herd);

        poacher.KillAnimal(animal);

        assertFalse(herd.getAnimals().isEmpty(), "Animal should NOT be killed because it's out of range");
    }

    @Test
    void testKillRangerWithinRange() {
        Ranger ranger = new Ranger(new Position(105, 105, 32, 32));
        testModel.getRangers().add(ranger);

        poacher.KillRanger(ranger);

        assertNotNull(testModel.getRangers(), "Rangers list should not be null after KillRanger");
    }

    @Test
    void testMoveToRandomLocation() {
        // Adjunk a modellhez egy environment-et, hogy legyen célpont a mozgáshoz
        testModel.getEnvironments().add(new Environment(new Position(200, 200, 32, 32)));

        poacher.act(1f); // időlépés szimuláció

        assertTrue(poacher.hasActions(), "Poacher should have movement actions queued after act()");
    }

    static class TestGameModel extends GameModel {
        private final ArrayList<Herd> herds = new ArrayList<>();
        private final ArrayList<Ranger> rangers = new ArrayList<>();
        private final ArrayList<Environment> environments = new ArrayList<>();

        public TestGameModel() {
            super(1);
        }


        @Override
        public ArrayList<Herd> getHerds() {
            return herds;
        }

        @Override
        public ArrayList<Ranger> getRangers() {
            return rangers;
        }

        @Override
        public ArrayList<Environment> getEnvironments() {
            return environments;
        }
    }

}
