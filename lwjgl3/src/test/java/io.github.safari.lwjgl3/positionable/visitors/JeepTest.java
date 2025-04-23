package io.github.safari.lwjgl3.positionable.visitors;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JeepTest {

        @Test
        void testJeepConstructorInitializesCorrectly() {
            Position startPosition = new Position(5, 10, 32,32);
            Jeep jeep = new Jeep(startPosition, null);

            assertEquals(startPosition, jeep.getPosition());
            assertTrue(jeep.isTostart());
            assertNotNull(jeep.getTourists());
            assertTrue(jeep.getTourists().isEmpty());
            assertNotNull(jeep.getTexture());
        }

        @ParameterizedTest //Mozgas tesztelese
        @CsvSource({
            "0, 0, 10, 0, 1.0",   // jobbra
            "0, 0, 0, 10, 1.0",   // fel
            "0, 0, 3, 4, 5.0",    // 3-4-5 háromszög
            "5, 5, 5, 5, 2.0",     // cél = jelenlegi pozíció
            "5, 5, 10, 10, 0"     //cel nem a jelenlegi hely, de 0 val megyunk
        })

        void testMoveTowards(float startX, float startY, float targetX, float targetY, float speed) {
            Position start = new Position(startX, startY, 32 ,32);
            Position target = new Position(targetX, targetY, 32 , 32);
            Jeep jeep = new Jeep(start, null);

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


        @Test //Turista hozzaadasa a Jeephez
        public void testAddTouristWhenUnderLimit() {
            Jeep jeep = new Jeep(new Position(0, 0, 32 , 32), null);

            for (int i = 0; i < 3; i++) {
                boolean result = jeep.trytoaddtourist(new Tourist(new Position(0,0,32,32), null));
                assertTrue(result, "Turista nem lett hozzáadva, pedig volt hely.");
            }

            boolean result = jeep.trytoaddtourist(new Tourist(new Position(0,0,32,32), null));
            assertTrue(result, "4. turista nem lett hozzáadva, pedig még belefér.");
        }

        @Test
        public void testAddTouristWhenAtLimit() {
            Jeep jeep = new Jeep(new Position(0, 0, 32 ,32), null);

            // 4 turista hozzaadasa
            for (int i = 0; i < 4; i++) {
                assertTrue(jeep.trytoaddtourist(new Tourist(new Position(0,0,32,32), null)));
            }

            // 5. turistat nem lehet hozzaadni
            boolean result = jeep.trytoaddtourist(new Tourist(new Position(0,0,32,32), null));
            assertFalse(result, "5. turista hozzá lett adva, de nem kellett volna.");
        }

        @Test
        public void testAddExactlyFourTourists() {
            Jeep jeep = new Jeep(new Position(0, 0, 32 , 32), null);

            for (int i = 0; i < 4; i++) {
                assertTrue(jeep.trytoaddtourist(new Tourist(new Position(0,0,32,32), null)), "Nem sikerült hozzáadni a(z) " + (i + 1) + ". turistát.");
            }

            assertEquals(4, jeep.getTourists().size(), "A turisták száma nem pontosan 4.");
        }

        @Test
        public void testAddOneTouristToList()
        {
            Jeep jeep = new Jeep(new Position(0, 0, 32 , 32), null);

            assertTrue(jeep.trytoaddtourist(new Tourist(new Position(0,0,32,32), null)), "Nem sikerült hozzáadni a turistát!");

            assertEquals(1, jeep.getTourists().size());
        }

        @ParameterizedTest //Turistak leadasanak ellenorzese
        @CsvSource({
            "0,0",
            "1,1",
            "2,2",
            "3,3"
        })
        public void testDropOffTourists(int toAddTourist, int expectedDropped) {
            Jeep jeep = new Jeep(new Position(0, 0, 32, 32), null);

            for (int i = 0; i < toAddTourist; i++) {
                assertTrue(jeep.trytoaddtourist(new Tourist(new Position(0,0,32,32), null)), "Nem sikerült hozzáadni a turistát!");
            }

            int droppedCount = jeep.Drop_Off_Tourists();

            assertEquals(expectedDropped, droppedCount, "Nem megfelelő számú turistát adott le.");
        }


    }


