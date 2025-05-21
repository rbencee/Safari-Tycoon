package io.github.safari.lwjgl3.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import io.github.safari.lwjgl3.maingame.ConstantCollector;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HerdFoodAndDrinkBarTest {

    private Herd herd;
    private OrthographicCamera camera;

    @BeforeEach
    public void setup() {
        herd = mock(Herd.class);
        camera = mock(OrthographicCamera.class);
        when(camera.project(any(Vector3.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testRenderWithEmptyHerdDoesNothing() {
        when(herd.getAnimals()).thenReturn(Collections.emptyList());

        HerdFoodAndDrinkBar bar = new HerdFoodAndDrinkBar(herd, camera);
        assertDoesNotThrow(bar::render);
    }

    @Test
    public void testRenderWithValidHerdDoesNotThrow() {
        Animal animal = mock(Animal.class);
        when(herd.getAnimals()).thenReturn((ArrayList<AnimalImpl>) Collections.singletonList(animal));
        when(herd.getMinHunger()).thenReturn(50.0);
        when(herd.getMinThirst()).thenReturn(75.0);
        when(herd.getPosition()).thenReturn(new Position(100, 100, 10, 10));

        HerdFoodAndDrinkBar bar = new HerdFoodAndDrinkBar(herd, camera);
        assertDoesNotThrow(bar::render);
    }

    @Test
    public void testDisposeDoesNotThrow() {
        HerdFoodAndDrinkBar bar = new HerdFoodAndDrinkBar(herd, camera);
        assertDoesNotThrow(bar::dispose);
    }
}
