package io.github.safari.lwjgl3.util.pathfinding;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DistanceCostConnectionTest {


    @Test
    void testConnectionFieldsAndCost() {
        Node from = new Node(new Vector2(0, 0), 0);
        Node to = new Node(new Vector2(3, 4), 1);

        DistanceCostConnection connection = new DistanceCostConnection(from, to);

        assertSame(from, connection.getFromNode());
        assertSame(to, connection.getToNode());
        assertEquals(5.0f, connection.getCost(), 0.001f);
    }
}
