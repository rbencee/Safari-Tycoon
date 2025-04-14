package io.github.safari.lwjgl3.util.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class Node {

    private final Vector2 vector2;
    public int index;
    public Array<Connection<Node>> connections = new Array<>();

    public Node(Vector2 vector2, int index) {
        this.vector2 = vector2;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public Vector2 getVector2() {
        return vector2;
    }

    public void addConnection(Connection<Node> c){
        connections.add(c);
    }

    @Override
    public String toString() {
        return vector2.toString();
    }
}

