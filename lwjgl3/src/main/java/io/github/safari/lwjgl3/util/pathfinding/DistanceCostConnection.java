package io.github.safari.lwjgl3.util.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;

public class DistanceCostConnection implements Connection<Node> {
    private final Node fromNode;
    private final Node toNode;
    private final float cost;

    public DistanceCostConnection(Node fromNode, Node toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = fromNode.getVector2().dst(toNode.getVector2());
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }

    @Override
    public float getCost() {
        return cost;
    }
}
