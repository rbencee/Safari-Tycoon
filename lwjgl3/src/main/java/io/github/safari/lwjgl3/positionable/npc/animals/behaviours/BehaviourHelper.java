package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.CloneableMoveToAction;
import io.github.safari.lwjgl3.util.pathfinding.PathFinderHelper;

import java.util.List;

public class BehaviourHelper {


    public static Array<Action> createMoveToActions(float speed, Vector2 start, Vector2 destination) {

        PathFinderHelper pfh = new PathFinderHelper();
        List<Vector2> path = pfh.findRoute(start, destination);

        int speedMultiplicator = GamemodelInstance.gameModel.getTimeMultiplicator();
        Array<Action> actions = new Array<>();
        Vector2 last = start;
        for (Vector2 vector2 : path) {
            float dist = vector2.dst(last);
            actions.add(new CloneableMoveToAction(Actions.moveTo(vector2.x, vector2.y, dist / (speed * speedMultiplicator))));
            last = vector2;
        }
        return actions;
    }

    public static Array<Action> createMoveToActions(float speed, List<Vector2> path) {
        int speedMultiplicator = GamemodelInstance.gameModel.getTimeMultiplicator();
        Array<Action> actions = new Array<>();

        if (path == null || path.size() < 2) return actions;

        Vector2 last = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            Vector2 current = path.get(i);
            float dist = current.dst(last);
            actions.add(new CloneableMoveToAction(Actions.moveTo(current.x, current.y, dist / (speed * speedMultiplicator))));
            last = current;
        }

        return actions;
    }
}
