package io.github.safari.lwjgl3.positionable.npc.human;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.BehaviourHelper;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.util.Positionable;
import io.github.safari.lwjgl3.util.pathfinding.PathFinderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ranger extends Actor implements Human, Positionable {
    private Texture texture;
    private Position position;
    private int speed = 40;
    private int shootRange = 300;
    private static final float MOVE_INTERVAL = 5.0f;
    private float moveTimer;
    private boolean isSelected = false;
    private ArrayList<Poacher> poachers = GamemodelInstance.gameModel.getPoachers();
    private static final float PATH_UPDATE_INTERVAL = 5.0f;
    private List<Vector2> currentPath;
    private float pathUpdateTimer = 0;
    private Positionable currentTarget;

    public Ranger(Position position) {
        this.position = position;
        if (Gdx.gl != null) {
            this.texture = new Texture("textures/humans/steve.png");
        }
        setPosition(position.getX(), position.getY());
        this.moveTimer = 0;

    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public ArrayList<Poacher> getPoachers() {return poachers;}

    public void setPoachers(ArrayList<Poacher> poachers) {this.poachers = poachers;}

    public Positionable getCurrentTarget() {return currentTarget;}



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
            moveTimer += delta;
            if (moveTimer >= MOVE_INTERVAL || !hasActions()) {
                moveTimer = 0;
                moveToRandomLocation();
                checkAndKillPoacher();
            }
        }
    }

    private void moveToRandomLocation() {
        Random rand = new Random();
        int n = rand.nextInt(GamemodelInstance.gameModel.getEnvironments().size());
        Environment e = GamemodelInstance.gameModel.getEnvironments().get(n);
        Vector2 randomDestination = new Vector2(
            e.getPosition().getX(),
            e.getPosition().getY()
        );

        Vector2 currentPos = new Vector2(getX(), getY());

        clearActions();

        Array<Action> moveActions = BehaviourHelper.createMoveToActions(speed, currentPos, randomDestination);

        for (Action action : moveActions) {
            addAction(Actions.after(action));
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
                    addAction(Actions.after(action));
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
                    killAnimal((Animal) currentTarget);
                } else if (currentTarget instanceof Poacher) {
                    killPoacher((Poacher) currentTarget);
                }
                currentTarget = null;
                currentPath = null;
                clearActions();
            }
        }
    }


    private void checkAndKillPoacher() {
        ArrayList<Poacher> poachers = GamemodelInstance.gameModel.getPoachers();
        Vector2 rangerPos = new Vector2(position.getX(), position.getY());

        for (Poacher poacher : poachers) {
            Vector2 poacherPos = new Vector2(poacher.getPosition().getX(), poacher.getPosition().getY());
            float distance = rangerPos.dst(poacherPos);

            if (distance <= shootRange) {
                if (Math.random() < 0.5) {
                    ArrayList<Poacher> tempList = new ArrayList<>(poachers);
                    boolean removed = tempList.remove(poacher);
                    if (removed) {
                        poachers.clear();
                        poachers.addAll(tempList);
                        System.out.println("Ranger killed a Poacher!");
                    }
                } else {
                    System.out.println("Ranger shot at Poacher but missed!");
                }
                break;
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

    public void killAnimal(Animal animal) {
        for (Herd h : GamemodelInstance.gameModel.getHerds()) {
            if (h.getAnimals().contains(animal)) {
                h.getAnimals().remove(animal);
                System.out.println("Ranger removed a problem animal.");
                break;
            }
        }
    }

    public void killPoacher(Poacher poacher) {
        ArrayList<Poacher> tempList = new ArrayList<>(poachers);
        boolean removed = tempList.remove(poacher);
        if (removed) {

            poachers.clear();
            poachers.addAll(tempList);
            if(Gdx.app != null) {
                Gdx.app.log("Ranger", "Poacher eliminated");
            }
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
