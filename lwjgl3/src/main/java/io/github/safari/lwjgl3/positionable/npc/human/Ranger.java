package io.github.safari.lwjgl3.positionable.npc.human;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.maingame.*;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.util.Positionable;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.BehaviourHelper;
import io.github.safari.lwjgl3.util.pathfinding.*;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class Ranger extends Actor implements Human, Positionable {
    private Texture texture;
    private Position position;
    private int speed = 15;
    private int shootRange = 125;
    private Animal targetAnimal;
    private static final float MOVE_INTERVAL = 5.0f;
    private float moveTimer;
    private boolean isSelected = false;
    private Poacher targetPoacher;
    private ArrayList<Poacher> poachers = GamemodelInstance.gameModel.getPoachers();
    private static final float PATH_UPDATE_INTERVAL = 5.0f;
    private List<Vector2> currentPath;
    private float pathUpdateTimer = 0;
    private Positionable currentTarget;

    public Ranger(Position position) {
        this.position = position;
        this.texture = new Texture("textures/humans/steve.png");
        setPosition(position.getX(), position.getY());
        this.moveTimer = 0;

    }



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }



    @Override
    public void act(float delta) {
        super.act(delta);

        this.position.setX((int) getX());
        this.position.setY((int) getY());

        if (currentTarget != null) {
            pathUpdateTimer += delta;
            if (pathUpdateTimer >= PATH_UPDATE_INTERVAL || currentPath == null) {
                pathUpdateTimer = 0;
                updatePathToTarget();
            }
            checkAndKillTarget();
        } else {
            // Véletlenszerű mozgás
            moveTimer += delta;
            if (moveTimer >= MOVE_INTERVAL || !hasActions()) {
                moveTimer = 0;
                moveToRandomLocation();
            }
        }
    }

    private void moveToRandomLocation() {
        float targetX = random.nextInt((int) GamemodelInstance.gameModel.getMapWidth());
        float targetY = random.nextInt((int) GamemodelInstance.gameModel.getMapWidth());

        Vector2 currentPos = new Vector2(getX(), getY());
        Vector2 targetPos = new Vector2(targetX, targetY);

        clearActions();

        Array<Action> moveActions = BehaviourHelper.createMoveToActions(speed, currentPos, targetPos);

        for (Action action : moveActions) {
            addAction(action);
        }
    }

    private void updatePathToTarget() {
        if (currentTarget != null) {
            Vector2 currentPos = new Vector2(getX(), getY());
            Vector2 targetPos = new Vector2(currentTarget.getPosition().getX(),
                currentTarget.getPosition().getY());

            PathFinderHelper pathFinder = new PathFinderHelper();
            currentPath = pathFinder.findRoute(currentPos, targetPos);

            if (currentPath != null && !currentPath.isEmpty()) {
                clearActions();
                Array<Action> actions = BehaviourHelper.createMoveToActions(speed, currentPath);
                for (Action action : actions) {
                    addAction(action);
                }
            }
        }
    }

    private void checkAndKillTarget() {
        if (currentTarget != null) {
            Vector2 rangerPos = new Vector2(getX(), getY());
            Vector2 targetPos = new Vector2(currentTarget.getPosition().getX(),
                currentTarget.getPosition().getY());

            if (rangerPos.dst(targetPos) <= shootRange) {
                if (currentTarget instanceof Animal) {
                    killAnimal((Animal)currentTarget);
                } else if (currentTarget instanceof Poacher) {
                    killPoacher((Poacher)currentTarget);
                }
                currentTarget = null;
                currentPath = null;
                clearActions();
            }
        }
    }

    public void setTargetAnimal(Animal animal) {
        this.currentTarget = (Positionable) animal;
        this.pathUpdateTimer = 0;
        updatePathToTarget();
    }

    public void setTargetPoacher(Poacher poacher) {
        this.currentTarget = poacher;
        this.pathUpdateTimer = 0;
        updatePathToTarget();
    }

    private void killAnimal(Animal animal) {
        for (Herd h : GamemodelInstance.gameModel.getHerds()) {
            if (h.getAnimals().contains(animal)) {
                h.getAnimals().remove(animal);
                System.out.println("Ranger removed a problem animal.");

                if (h.getAnimals().isEmpty()) {
                    GamemodelInstance.gameModel.getHerds().remove(h);
                    System.out.println("Herd removed (empty).");
                }
                break;
            }
        }
    }

    private void killPoacher(Poacher poacher) {
        ArrayList<Poacher> tempList = new ArrayList<>(poachers);
        boolean removed = tempList.remove(poacher);
        if (removed) {

            poachers.clear();
            poachers.addAll(tempList);
            System.out.println("Ranger eliminated a poacher.");
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(position.getX(), position.getY(),
            position.getWidth(), position.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }
}
