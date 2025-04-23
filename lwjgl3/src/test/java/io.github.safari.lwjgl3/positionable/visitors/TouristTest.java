package io.github.safari.lwjgl3.positionable.visitors;

import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.positionable.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TouristTest {

    private Position testPosition;
    private Texture mockTexture;

    @BeforeEach
    void setup() {
        testPosition = new Position(5, 10, 32, 32);
        mockTexture = mock(Texture.class);
    }

    @Test
    void constructorShouldSetPositionAndTexture() {
        Tourist tourist = new Tourist(testPosition, mockTexture);

        assertEquals(testPosition, tourist.getPosition(), "Position is not set correctly.");
        assertEquals(mockTexture, tourist.getTexture(), "Texture should be correctly set");
        assertTrue(tourist.isShown(), "Tourist should be shown by default");
    }

    @Test
    void setShownShouldUpdateVisibility() {
        Tourist tourist = new Tourist(testPosition, mockTexture);
        tourist.setShown(false);

        assertFalse(tourist.isShown(), "Tourist should not be shown after setting shown to false");

        tourist.setShown(true);
        assertTrue(tourist.isShown(), "Tourist should be shown after setting shown to true");
    }

}
