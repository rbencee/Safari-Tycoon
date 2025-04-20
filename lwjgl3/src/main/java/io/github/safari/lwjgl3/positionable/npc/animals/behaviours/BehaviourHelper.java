package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.util.pathfinding.PathFinderHelper;

import java.util.ArrayList;
import java.util.List;

public class BehaviourHelper {

    public static Array<Action> createMoveToActions(float speed, Vector2 start, Vector2 destination) {

        PathFinderHelper pfh = new PathFinderHelper();
        List<Vector2> path = pfh.findRoute(start, destination);

        Array<Action> actions = new Array<>();
        Vector2 last = start;
        for (Vector2 vector2 : path) {
            float dist = vector2.dst(last);
            actions.add(Actions.after(Actions.moveTo(vector2.x, vector2.y, dist/speed)));
            last = vector2;
        }
        MoveToAction move = null;
        return actions;
    }
}
