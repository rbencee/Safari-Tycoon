package io.github.safari.lwjgl3.util.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    static class DummyConnection implements Connection<Node> {
        private final Node from;
        private final Node to;
        private final float cost;

        public DummyConnection(Node from, Node to, float cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public Node getFromNode() {
            return from;
        }

        @Override
        public Node getToNode() {
            return to;
        }

        @Override
        public float getCost() {
            return cost;
        }
    }

    @Test
    void testNodeFieldsAndMethods() {
        Vector2 position = new Vector2(2, 3);
        Node node = new Node(position, 1);

        assertEquals(1, node.getIndex());
        assertEquals(position, node.getVector2());
        assertTrue(node.getConnections().isEmpty());

        Node other = new Node(new Vector2(5, 5), 2);
        Connection<Node> connection = new DummyConnection(node, other, 7.0f);
        node.addConnection(connection);

        assertEquals(1, node.getConnections().size);
        assertSame(connection, node.getConnections().first());

        assertEquals("(2.0,3.0)", node.toString());
    }
}
