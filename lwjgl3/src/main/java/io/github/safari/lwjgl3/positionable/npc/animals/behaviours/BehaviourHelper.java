package io.github.safari.lwjgl3.positionable.npc.animals.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.util.pathfinding.PathFinderHelper;

import java.util.ArrayList;
import java.util.List;

public class BehaviourHelper {

    public static Array<Action> addMoveToActions(Herd herd, Vector2 start, Vector2 destination) {

        ArrayList<Position> obstacles = new ArrayList<>();
        for (Environment e : GamemodelInstance.gameModel.getEnvironments()){
            obstacles.add(e.getPosition());
        }

        PathFinderHelper pfh = new PathFinderHelper();
        List<Vector2> path = pfh.findRoute(start, destination, obstacles);

        Array<Action> actions = new Array<>();
        for (Vector2 vector2 : path) {
            actions.add(Actions.after(Actions.moveTo(vector2.x, vector2.y, 5))); //todo speed
        }
        return actions;
    }
}
