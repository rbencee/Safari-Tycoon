package io.github.safari.lwjgl3.maingame;

import static org.junit.jupiter.api.Assertions.*;

import io.github.safari.lwjgl3.maingame.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameControllerTest {
    /*

    private GameController controller;

    @BeforeEach
    public void setUp() {
        controller = new GameController();
    }

    @Test
    public void testInitialMoneyIsCorrect() {
        assertEquals(10000, controller.getGameModel().getMoney());
    }

    @Test
    public void testAddAndRemoveRoad() {
        Road road = new Road(1, 1);
        controller.addRoad(road);
        assertTrue(controller.getGameModel().getRoads().contains(road));

        controller.removeRoad(road);
        assertFalse(controller.getGameModel().getRoads().contains(road));
    }

    @Test
    public void testAddAndRemoveAnimal() {
        Animal animal = new Animal(2, 2, AnimalType.HERBIVORE);
        controller.addAnimal(animal);
        assertTrue(controller.getGameModel().getAnimals().contains(animal));

        controller.removeAnimal(animal);
        assertFalse(controller.getGameModel().getAnimals().contains(animal));
    }

    @Test
    public void testAddAndRemoveEnvironment() {
        Environment environment = new Environment(3, 3, EnvironmentType.TREE);
        controller.addEnvironment(environment);
        assertTrue(controller.getGameModel().getEnvironments().contains(environment));

        controller.removeEnvironment(environment);
        assertFalse(controller.getGameModel().getEnvironments().contains(environment));
    }

    @Test
    public void testAddAndRemoveJeep() {
        Jeep jeep = new Jeep(4, 4);
        controller.addJeep(jeep);
        assertTrue(controller.getGameModel().getJeeps().contains(jeep));

        controller.removeJeep(jeep);
        assertFalse(controller.getGameModel().getJeeps().contains(jeep));
    }

    @Test
    public void testSetAndGetSelected() {
        Object obj = new Object();
        controller.setSelected(obj);
        assertEquals(obj, controller.getSelected());
    }

    @Test
    public void testSetAndGetToPlace() {
        Object obj = new Object();
        controller.setToPlace(obj);
        assertEquals(obj, controller.getToPlace());
    }

    @Test
    public void testSetAndGetMode() {
        GameController.Mode mode = GameController.Mode.BUY;
        controller.setMode(mode);
        assertEquals(mode, controller.getMode());
    }

    @Test
    public void testSetAndGetSelectedTile() {
        Vector2 tile = new Vector2(5, 5);
        controller.setSelectedTile(tile);
        assertEquals(tile, controller.getSelectedTile());
    }

    @Test
    public void testGameModelIsNotNull() {
        assertNotNull(controller.getGameModel());
    }

     */
}
