package io.github.safari.lwjgl3.util.pathfinding;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.Position;

import java.util.ArrayList;
import java.util.List;

public class PathGraph implements IndexedGraph<Node>{
    private Array<Node> nodes;
    PathHeuristic pathHeuristic = new PathHeuristic();

    public PathGraph() {
        System.out.println("graph created");
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

    public Array<Node> generateNodes(Vector2 start, Vector2 destination, ArrayList<Position> obstacles){
        Array<Node> nodes = new Array<>();

        int index = 0;
        Node startNode = new Node(start, index++);
        Node endNode = new Node(destination, index++);
        nodes.add(startNode);
        nodes.add(endNode);
        for (Position o : obstacles){
            nodes.add(new Node(new Vector2(o.getX()-1, o.getY()-1), index++));
            nodes.add(new Node(new Vector2(o.getX()+o.getWidth()+1, o.getY()-1), index++));
            nodes.add(new Node(new Vector2(o.getX()-1, o.getY()+o.getHeight()+1), index++));
            nodes.add(new Node(new Vector2(o.getX()+o.getWidth()+1, o.getY()+o.getHeight()+1), index++));
        }
        return nodes;
    }

    private void connectVisibleNodes(ArrayList<Position> obstacles){
        for (int i = 0; i < nodes.size; i++) {
            Node a = nodes.get(i);
            for (int j = 0; j < nodes.size; j++) {
                if (j == i) continue;
                Node b = nodes.get(j);
                if (isNodeVisible(a.getVector2(), b.getVector2(), obstacles)) {
                    a.addConnection(new DefaultConnection<>(a, b));
                }
            }
        }
    }

    private boolean isNodeVisible(Vector2 vect1, Vector2 vect2, ArrayList<Position> obstacles){
        for (Position o : obstacles) {
            Rectangle rect = new Rectangle(o.getX(), o.getY(), o.getWidth(), o.getHeight());
            if (Intersector.intersectSegmentRectangle(vect1, vect2, rect)){
                //todo probléma: mivel saroktól sarokig megyek, a sarok még pont "belelóg" az útvonalba, ezért false-t ad vissza
                // kell olyan függvény h a sarok ne számítson
                // vagy 1 távolsággal arrébbi pontokat kell összekötögetni
                // ugyanez a probléma, ha egy rectangle oldala mentén menne, akkor az az oldal még pont "belelóg"
                return false;
            }
        }
        return true;
    }


    public List<Vector2> findRoute(Vector2 startPos, Vector2 endPos, ArrayList<Position> obstacles) {
        this.nodes = generateNodes(startPos, endPos, obstacles);
        connectVisibleNodes(obstacles);

        Node startNode = nodes.get(0);
        Node endNode = nodes.get(1);

        PathHeuristic heuristic = new PathHeuristic();

        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(this);
        DefaultGraphPath<Node> path = new DefaultGraphPath<>();

        boolean found = pathFinder.searchNodePath(startNode, endNode, heuristic, path);

        List<Vector2> result = new ArrayList<>();
        if (found) {
            for (Node node : path) {
                result.add(node.getVector2());
            }
        }
        return result;
    }



}
