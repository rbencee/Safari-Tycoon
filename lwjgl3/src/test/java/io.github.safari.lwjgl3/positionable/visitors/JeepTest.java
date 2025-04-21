package io.github.safari.lwjgl3.positionable.Jeep;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class JeepTest {

import io.github.safari.lwjgl3.maingame.aa.Jeep;
import io.github.safari.lwjgl3.maingame.aa.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    class GameModelTest {

        @Test
        void testJeepConstructorInitializesCorrectly() {
            Position startPosition = new Position(5, 10);
            Jeep jeep = new Jeep(startPosition);

            assertEquals(startPosition, jeep.getPosition());
            assertTrue(jeep.isToStart());
            assertNotNull(jeep.getTourists());
            assertTrue(jeep.getTourists().isEmpty());
            assertNotNull(jeep.getTexture());
        }

        @ParameterizedTest
        @CsvSource({
            "0, 0, 10, 0, 1.0",   // jobbra
            "0, 0, 0, 10, 1.0",   // fel
            "0, 0, 3, 4, 5.0",    // 3-4-5 háromszög
            "5, 5, 5, 5, 2.0"     // cél = jelenlegi pozíció
        })

        void testMoveTowards(float startX, float startY, float targetX, float targetY, float speed) {
            Position start = new Position(startX, startY);
            Position target = new Position(targetX, targetY);
            Jeep jeep = new Jeep(start);

            jeep.moveTowards(target, speed);

            float dx = targetX - startX;
            float dy = targetY - startY;
            float length = (float)Math.sqrt(dx * dx + dy * dy);

            float expectedX = startX;
            float expectedY = startY;

            if (length != 0) {
                expectedX += dx / length * speed;
                expectedY += dy / length * speed;
            }

            assertEquals(expectedX, jeep.getPosition().getX(), 0.001);
            assertEquals(expectedY, jeep.getPosition().getY(), 0.001);
        }


    }



}

