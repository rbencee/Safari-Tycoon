package io.github.safari.lwjgl3.positionable;

import io.github.safari.lwjgl3.positionable.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void testCloneAndToString() {
        Position original = new Position(3f, 4f, 10, 20);
        Position clone = original.clone();

        assertNotSame(original, clone);
        assertEquals(original, clone);
        assertEquals("Position{y=4.0, x=3.0}", original.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 3, 4, 5",
        "1, 2, 4, 6, 5",
        "-1, -1, 2, 3, 5",
        "0, 0, 0, 0, 0"
    })
    void testDistance(float x1, float y1, float x2, float y2, float expectedDistance) {
        Position p1 = new Position(x1, y1, 0, 0);
        Position p2 = new Position(x2, y2, 0, 0);
        assertEquals(expectedDistance, Position.distance(p1, p2), 0.0001);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 3, 4, 25",
        "1, 2, 4, 6, 25",
        "-1, -1, 2, 3, 25",
        "0, 0, 0, 0, 0"
    })
    void testDistanceSquared(float x1, float y1, float x2, float y2, float expectedDistance2) {
        Position p1 = new Position(x1, y1, 0, 0);
        Position p2 = new Position(x2, y2, 0, 0);
        assertEquals(expectedDistance2, Position.distance2(p1, p2), 0.0001);
    }

    @ParameterizedTest
    @MethodSource("provideEqualPositions")
    void testEquals(Position p1, Position p2, boolean expected) {
        assertEquals(expected, p1.equals(p2));
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideEqualPositions() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of(new Position(1, 2, 10, 10), new Position(1, 2, 10, 10), true),
            org.junit.jupiter.params.provider.Arguments.of(new Position(1, 2, 10, 10), new Position(2, 2, 10, 10), false),
            org.junit.jupiter.params.provider.Arguments.of(new Position(1, 2, 10, 10), null, false),
            org.junit.jupiter.params.provider.Arguments.of(new Position(1, 2, 10, 10), new Position(1, 3, 10, 10), false)
        );
    }
}
