package io.github.safari.lwjgl3.util.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class PathHeuristic implements Heuristic<Node> {


    @Override
    public float estimate(Node node, Node endNode) {
        return node.getVector2().dst(endNode.getVector2()); //todo euklidészi távolság
    }
}
