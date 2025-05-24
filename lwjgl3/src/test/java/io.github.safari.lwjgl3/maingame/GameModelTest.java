package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalType;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.SpeciesData;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.SpeciesFactory;
import io.github.safari.lwjgl3.positionable.npc.human.Poacher;
import io.github.safari.lwjgl3.positionable.npc.human.Ranger;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.positionable.visitors.Tourist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest extends LibGDXHeadlessTest{

    private GameModel gameModel;
    private final int TEST_DIFFICULTY = 1;

    @BeforeEach
    void setUp() {
        SpeciesFactory.speciesCache.clear();

        SpeciesFactory.speciesCache.put(AnimalSpecies.CAPYBARA,
            new SpeciesData(AnimalType.HERBIVORE, AnimalSpecies.CAPYBARA, 100, 1f, 200f, 10, null));

        SpeciesFactory.speciesCache.put(AnimalSpecies.LION,
            new SpeciesData(AnimalType.PREDATOR, AnimalSpecies.LION, 100, 1.2f, 250f, 12, null));

        SpeciesFactory.loaded = true;

        gameModel = new GameModel(TEST_DIFFICULTY);
    }

    @AfterEach
    void tearDown() {
        SpeciesFactory.speciesCache.clear();
        SpeciesFactory.loaded = false;
    }



    @Test
    void testInitialization() {
        assertEquals(5000, gameModel.getMoney());
        assertEquals(0, gameModel.getDayspassed());
        assertEquals(1, gameModel.getSpeed());
        assertEquals(TEST_DIFFICULTY, gameModel.getDifficulty());
        assertFalse(gameModel.isGameWon());
        assertFalse(gameModel.isGameOver());
    }

    @Test
    void testSpeedSettings() {
        gameModel.setSpeed(2);
        assertEquals(2, gameModel.getSpeed());
        assertEquals(7, gameModel.getTimeMultiplicator());

        gameModel.setSpeed(3);
        assertEquals(3, gameModel.getSpeed());
        assertEquals(30, gameModel.getTimeMultiplicator());

        gameModel.setSpeed(1);
        assertEquals(1, gameModel.getSpeed());
        assertEquals(1, gameModel.getTimeMultiplicator());
    }

    @Test
    void testMoneyManagement() {
        gameModel.increasemoney(1000);
        assertEquals(6000, gameModel.getMoney());

        gameModel.Decrease_My_Money(2000);
        assertEquals(4000, gameModel.getMoney());
    }

    @Test
    void testPositionFound() {
        gameModel = new GameModel(1);
        gameModel.getEnvironments().clear();
        assertTrue(gameModel.positionFound(100, 100, 32, 32));
        assertFalse(gameModel.positionFound(-50, 100, 32, 32));
        assertFalse(gameModel.positionFound(100, -50, 32, 32));
        assertFalse(gameModel.positionFound(gameModel.getMapWidth() + 50, 100, 32, 32));
        assertFalse(gameModel.positionFound(100, gameModel.getMapHeight() + 50, 32, 32));

        Environment tree = new Tree(new Position(200, 200, 64, 64));
        gameModel.addtoenvironment(tree);
        assertFalse(gameModel.positionFound(200, 200, 32, 32));
    }

    @Test
    void testGameOverConditions() {
        GameModel poorModel = new GameModel(1);
        poorModel.Decrease_My_Money(500000);
        assertTrue(poorModel.isGameOver());
    }

    @Test
    void testAnimalCounting() {
        Herd herbherd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        Herd predherd = new Herd(AnimalSpecies.LION, Behaviour.createPredatorBehaviours());

        for(int i = 0; i < 15; i++) {
            Animal herbanimal = AnimalFactory.createNew(AnimalSpecies.CAPYBARA, new Position(0, 0, 32, 32));
            herbherd.addToHerd((AnimalImpl) herbanimal);
        }

        for(int i = 0; i < 17; i++) {
            Animal predanimal = AnimalFactory.createNew(AnimalSpecies.LION, new Position(500, 500, 32, 32));
            predherd.addToHerd((AnimalImpl) predanimal);
        }

        gameModel.getHerds().add(herbherd);
        gameModel.getHerds().add(predherd);

        assertEquals(32, gameModel.sumAnimals());
        assertEquals(15, gameModel.sumHerbivores());
        assertEquals(17, gameModel.sumPredators());
        assertEquals(2, gameModel.sumUniqueAnimals());
    }

    @Test
    void testTouristManagement() {
        gameModel.setTouristcount(5);
        assertEquals(5, gameModel.getTouristcount());
        gameModel.setTouristcount(0);
        gameModel.SummonTourist();
    }

    @Test
    void testRoadManagement() {
        Road testRoad = new Road(new Position(100, 100, 64, 64), 1);
        gameModel.addtoroads(testRoad);

        assertTrue(gameModel.Is_There_Road(100, 100));
        assertFalse(gameModel.Is_There_Road(200, 200));

        assertNotNull(gameModel.getRoads());
        assertEquals(3, gameModel.getRoads().size());
        assertNotNull(gameModel.getExitRoad());
        assertNotNull(gameModel.getEntranceRoad());
    }

    @Test
    void testPoacherManagement() {
        assertEquals(5, gameModel.getPoachers().size());
        gameModel.spawnRandomPoachers();
        assertTrue(gameModel.getPoachers().size() >= 5);
    }

    @Test
    void testIncomeCalculation() {
        Animal herbanimal = AnimalFactory.createNew(AnimalSpecies.CAPYBARA, new Position(0, 0, 32, 32));
        Herd herbherd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
        herbherd.addToHerd((AnimalImpl) herbanimal);

        Animal predanimal = AnimalFactory.createNew(AnimalSpecies.LION, new Position(500, 500, 32, 32));
        Herd predherd = new Herd(AnimalSpecies.LION, Behaviour.createPredatorBehaviours());
        predherd.addToHerd((AnimalImpl) predanimal);

        gameModel.getHerds().add(herbherd);
        gameModel.getHerds().add(predherd);

        gameModel.getRangers().add(new Ranger(new Position(0, 0, 64, 64)));
        gameModel.calculateIncome();
        assertEquals(-38, gameModel.getIncome());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testDifficultySettings(int difficulty) {
        gameModel.setDifficulty(difficulty);
        assertEquals(difficulty, gameModel.getDifficulty());
    }

    @Test
    void testEnvironmentManagement() {
        int initialCount = gameModel.getEnvironments().size();
        Tree tree = new Tree(new Position(500, 500, 64, 64));
        gameModel.addtoenvironment(tree);
        assertEquals(initialCount + 1, gameModel.getEnvironments().size());
    }

    @Test
    void testJeepManagement() {
        int initialCount = gameModel.getJeeps().size();
        Jeep jeep = new Jeep(new Position(300, 300, 64, 64));
        gameModel.addtojeeps(jeep);
        assertEquals(initialCount + 1, gameModel.getJeeps().size());
    }

    @Test
    void testWinCondition() {
        gameModel.setTouristcount(100);

        assertTrue(gameModel.getTouristcount() == 100);

        Herd herbherd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());;
        Herd predherd = new Herd(AnimalSpecies.LION, Behaviour.createPredatorBehaviours());

        for(int i = 0; i < 100; i++) {
            Animal herbanimal = AnimalFactory.createNew(AnimalSpecies.CAPYBARA, new Position(0, 0, 32, 32));
            herbherd.addToHerd((AnimalImpl) herbanimal);

            Animal predanimal = AnimalFactory.createNew(AnimalSpecies.LION, new Position(500, 500, 32, 32));
            predherd.addToHerd((AnimalImpl) predanimal);
        }

        gameModel.getHerds().add(herbherd);
        gameModel.getHerds().add(predherd);
        gameModel.increasemoney(100000);

        for(int i = 0; i < 100; i++) {
            gameModel.checkwincon();
        }

        assertNotNull(gameModel.getHerds());
        assertNotNull(gameModel.getRangers());


        assertEquals(105000, gameModel.getMoney());
        assertTrue(gameModel.checkwincon());

        for(int i = 0; i < 100; i++) {
            gameModel.Simulation(100);
        }

        assertEquals(100, gameModel.getDayspassed());

        assertTrue(gameModel.isGameWon());
    }

    @Test
    void testCanBuy() {
        ShopItem expensiveItem = new ShopItem("Test", 600000);
        ShopItem affordableItem = new ShopItem("Test", 2000);

        assertFalse(gameModel.CanBuy(expensiveItem));
        assertTrue(gameModel.CanBuy(affordableItem));
    }
}
