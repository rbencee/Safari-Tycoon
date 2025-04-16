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

    public PathFinderHelper() {}

    public List<Vector2> findRoute(Vector2 startPos, Vector2 endPos, ArrayList<Position> obstacles) {
        int index = PathGraph.STATIC_NODES.size;
        if (startPos.equals(endPos)) return new ArrayList<>();

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

        PathGraph tempGraph = new PathGraph(allNodes);


        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(tempGraph);
        DefaultGraphPath<Connection<Node>> path = new DefaultGraphPath<>();

        boolean found = pathFinder.searchConnectionPath(startNode, endNode, new PathHeuristic(), path);

        List<Vector2> result = new ArrayList<>();
        if (found) {
            for (Connection<Node> c : path) {
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
