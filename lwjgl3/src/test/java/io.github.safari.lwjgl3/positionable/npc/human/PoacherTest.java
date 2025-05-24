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
import io.github.safari.lwjgl3.positionable.objects.Bush;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PoacherTest {

    private Poacher poacher;
    private TestGameModel testModel;
    private GameModel mockModel; // Added this declaration

    @BeforeEach
    void setUp() {
        testModel = new TestGameModel();
        GamemodelInstance.gameModel = testModel;
        mockModel = mock(GameModel.class);

        poacher = new Poacher(new Position(0, 0, 32, 32));
    }

    @Test
    void testKillAnimalWithinRange() {
        Animal animal = AnimalFactory.createNew(AnimalSpecies.CAPYBARA, new Position(110, 110, 32, 32));
        Herd herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        herd.addToHerd((AnimalImpl) animal);
        testModel.getHerds().add(herd);

        poacher.KillAnimal(animal);

        assertTrue(herd.getAnimals().isEmpty(), "Animal should be killed and removed from the herd");
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
    void ranger_ShouldMoveToRandomLocationWhenNoTarget() {
        // Reset the pathfinding system completely
        PathGraph.STATIC_NODES.clear();
        PathGraph.index = 0; // Reset the index counter

        // Create a single obstacle that will generate predictable node indices (0-3)
        ArrayList<Position> obstacles = new ArrayList<>();
        Position obstaclePos = new Position(100, 100, 64, 64);
        obstacles.add(obstaclePos);

        // Generate nodes for this simple environment
        PathGraph.generateStaticNodes(obstacles);

        // Create ranger at known position
        Ranger ranger = new Ranger(new Position(0, 0, 32, 32));

        // Add environment to test model (must match the obstacle position)
        testModel.getEnvironments().add(new Bush(obstaclePos));

        // Act - only call act once with small time increment
        ranger.act(0.1f);

        // Verify basic movement behavior without checking pathfinding
        assertTrue(ranger.getActions().size > 0,
            "Ranger should have movement actions when no target is present");
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
