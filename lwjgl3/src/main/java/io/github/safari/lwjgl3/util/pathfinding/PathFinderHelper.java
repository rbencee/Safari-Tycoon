package io.github.safari.lwjgl3.util.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.Position;

import java.util.ArrayList;
import java.util.List;

public class PathFinderHelper {
    private PathGraph graph;

    public PathFinderHelper() {
        System.out.println("pathfinderhelper created");
    }

    public List<Vector2> findRoute(Vector2 startPos, Vector2 endPos, ArrayList<Position> obstacles) {
        int index = PathGraph.STATIC_NODES.size;

        Node startNode = new Node(startPos, index);
        Node endNode = new Node(endPos, index +1 );

        for (Node node : PathGraph.STATIC_NODES) {
            if (PathGraph.isNodeVisible(startNode.getVector2(), node.getVector2(), obstacles)) {
                startNode.addConnection(new DistanceCostConnection(startNode, node));
            }
            if (PathGraph.isNodeVisible(endNode.getVector2(), node.getVector2(), obstacles)) {
                node.addConnection(new DistanceCostConnection(node, endNode));
            }
        }
        Array<Node> allNodes = new Array<>(PathGraph.STATIC_NODES);
        allNodes.add(startNode);
        allNodes.add(endNode);

        this.graph = new PathGraph(allNodes);


        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(this.graph);
        DefaultGraphPath<Connection<Node>> path = new DefaultGraphPath<>();

        boolean found = pathFinder.searchConnectionPath(startNode, endNode, new PathHeuristic(), path);
        System.out.println("(graph) route found: " + found);

        List<Vector2> result = new ArrayList<>();
        if (found) {
            System.out.println("(graph) route:");
            for (Connection<Node> c : path) {
                System.out.println(c.getToNode().getVector2());
                result.add(c.getToNode().getVector2());
            }
        }

        startNode.getConnections().clear();
        for (Node node : PathGraph.STATIC_NODES) {
            Array<Connection<Node>> connections = node.getConnections();
            for (Connection<Node> c : node.getConnections()){
                if (c.getToNode().equals(endNode)){
                    connections.removeValue(c,true);
                }
            }
        }
        return result;
    }
}
