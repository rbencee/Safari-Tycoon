package io.github.safari.lwjgl3.util.pathfinding;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.Position;

import java.util.ArrayList;

public record PathGraph(Array<Node> nodes) implements IndexedGraph<Node> {
    public static Array<Node> STATIC_NODES = new Array<>();

    public PathGraph(Array<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public int getIndex(Node node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }

    public static void generateStaticNodes(ArrayList<Position> obstacles) {
        STATIC_NODES.clear();
        int index = 0;
        for (Position o : obstacles) {
            STATIC_NODES.add(new Node(new Vector2(o.getX(), o.getY()), index++));
            STATIC_NODES.add(new Node(new Vector2(o.getX() + o.getWidth(), o.getY()), index++));
            STATIC_NODES.add(new Node(new Vector2(o.getX(), o.getY() + o.getHeight()), index++));
            STATIC_NODES.add(new Node(new Vector2(o.getX() + o.getWidth(), o.getY() + o.getHeight()), index++));
        }
        connectVisibleNodes(obstacles);
        System.out.println("(PathGraph) Static nodes generated");
    }

    private static void connectVisibleNodes(ArrayList<Position> obstacles) {
        for (int i = 0; i < STATIC_NODES.size; i++) {
            Node a = STATIC_NODES.get(i);
            for (int j = 0; j < STATIC_NODES.size; j++) {
                if (j == i) continue;
                Node b = STATIC_NODES.get(j);
                if (isNodeVisible(a.getVector2(), b.getVector2(), obstacles)) {
                    a.addConnection(new DistanceCostConnection(a, b));
                }
            }
        }
        System.out.println("(PathGraph) Visible nodes connected");
    }

    public static boolean isNodeVisible(Vector2 vect1, Vector2 vect2, ArrayList<Position> obstacles) {
        for (Position o : obstacles) {
            Rectangle rect = new Rectangle(o.getX() + 1, o.getY() + 1, o.getWidth() - 2, o.getHeight() - 2);
            if (Intersector.intersectSegmentRectangle(vect1, vect2, rect)) {
                return false;
            }
        }
        return true;
    }
}
