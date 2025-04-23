package io.github.safari.lwjgl3.positionable.objects;

import io.github.safari.lwjgl3.positionable.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    @Test
    void testBushCreation() {
        Position pos = new Position(10, 20, 32, 32);
        Bush bush = new Bush(pos);
        assertNotNull(bush);
        assertEquals(pos, bush.getPosition());
        assertTrue(bush instanceof HerbivoreEdible);
    }

    @Test
    void testGrassCreation() {
        Position pos = new Position(5, 15, 16, 16);
        Grass grass = new Grass(pos);
        assertNotNull(grass);
        assertEquals(pos, grass.getPosition());
        assertTrue(grass instanceof HerbivoreEdible);
    }

    @Test
    void testLakeCreation() {
        Position pos = new Position(0, 0, 64, 64);
        Lake lake = new Lake(pos);
        assertNotNull(lake);
        assertEquals(pos, lake.getPosition());
        assertTrue(lake instanceof Drinkable);
    }

    @Test
    void testTreeCreation() {
        Position pos = new Position(7, 13, 24, 24);
        Tree tree = new Tree(pos);
        assertEquals(pos, tree.getPosition());
        assertTrue(tree instanceof HerbivoreEdible);
    }


    @Test
    void testDefaultRoad() {
        Position pos = new Position(100, 200, 32, 32);
        Road road = new Road(pos, 1 , null);
        assertNotNull(road);
        assertEquals(pos, road.getPosition());
        assertEquals(1, road.getRoadtype());
    }

    @Test
    void testRoadWithType2() {
        Position pos = new Position(50, 60, 32, 32);
        Road road = new Road(pos, 2, null);
        assertNotNull(road);
        assertEquals(2, road.getRoadtype());
    }

    @Test
    void testRoadWithType3() {
        Position pos = new Position(70, 80, 32, 32);
        Road road = new Road(pos, 3, null);
        assertNotNull(road);
        assertEquals(3, road.getRoadtype());
        ///
    }
}
