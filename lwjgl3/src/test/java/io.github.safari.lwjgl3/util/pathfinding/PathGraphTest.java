package io.github.safari.lwjgl3.util.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PathGraphTest {

    @BeforeEach
    public void resetStaticNodes() {
        PathGraph.STATIC_NODES.clear();
    }

    @Test
    public void testAddObstacle_addsFourNodes() {
        Position obstacle = new Position(10, 10, 5, 5);
        PathGraph.addObstacle(obstacle);
        assertEquals(4, PathGraph.STATIC_NODES.size);
    }

    @Test
    public void testGenerateStaticNodes_createsConnectionsBetweenVisibleNodes() {
        ArrayList<Position> obstacles = new ArrayList<>();
        obstacles.add(new Position(0, 0, 10, 10));
        obstacles.add(new Position(50, 0, 10, 10)); // Far away, should connect

        PathGraph.generateStaticNodes(obstacles);

        for (Node node : PathGraph.STATIC_NODES) {
            Array<Connection<Node>> connections = node.getConnections();
            for (Connection<Node> conn : connections) {
                assertNotNull(conn);
                assertTrue(conn.getCost() > 0);
            }
        }

        // Should create 8 nodes (4 per obstacle)
        assertEquals(8, PathGraph.STATIC_NODES.size);
    }

    @Test
    public void testIsNodeVisible_trueWhenNoObstacles() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(10, 10);
        ArrayList<Position> noObstacles = new ArrayList<>();

        assertTrue(PathGraph.isNodeVisible(a, b, noObstacles));
    }

    @Test
    public void testIsNodeVisible_falseWhenObstacleBlocksView() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(10, 10);
        ArrayList<Position> obstacles = new ArrayList<>();
        obstacles.add(new Position(4, 4, 5, 5));

        assertFalse(PathGraph.isNodeVisible(a, b, obstacles));
    }

    @Test
    public void testGraphInterfaceMethods() {
        Node nodeA = new Node(new Vector2(1, 1), 0);
        Node nodeB = new Node(new Vector2(2, 2), 1);
        nodeA.addConnection(new DistanceCostConnection(nodeA, nodeB));

        Array<Node> nodes = new Array<>();
        nodes.add(nodeA);
        nodes.add(nodeB);

        PathGraph graph = new PathGraph(nodes);

        assertEquals(0, graph.getIndex(nodeA));
        assertEquals(2, graph.getNodeCount());

        Array<Connection<Node>> conns = graph.getConnections(nodeA);
        assertEquals(1, conns.size);
        assertEquals(nodeB, conns.get(0).getToNode());
    }
}
