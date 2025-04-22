package io.github.safari.lwjgl3.positionable.npc.human;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import io.github.safari.lwjgl3.positionable.Moveable;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.maingame.*;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.Iterator;
import java.util.Random;

import static io.github.safari.lwjgl3.positionable.npc.animals.behaviours.BehaviourHelper.createMoveToActions;

public class Poacher extends Actor implements Moveable, Positionable {
    private boolean isAlive;
    private int shootRange;
    private Texture texture;
    private Position position;
    private int speed;
    private Random random;
    private boolean isMoving;
    private float movementTimer;
    private static final float MOVEMENT_INTERVAL = 3.0f;

    public Poacher(Position position) {
        this.isAlive = true;
        this.position = position;
        this.texture = new Texture("textures/humans/tourist.png");
        this.shootRange = 400;
        this.speed = 5;
        this.random = new Random();
        this.isMoving = false;
        this.movementTimer = 0;
    }

    public void KillAnimal(Animal target) {
        if (target != null) {
            Vector2 poacherPos = new Vector2(position.getX(), position.getY());
            Vector2 animalPos = new Vector2(target.getPosition().getX(), target.getPosition().getY());

            float distance = poacherPos.dst(animalPos);

            if (distance <= shootRange) {
                for (Herd h : GamemodelInstance.gameModel.getHerds()) {
                    if (h.getAnimals().contains(target)) {
                        h.getAnimals().remove(target);
                        System.out.println("Poacher killed an animal!");

                        if (h.getAnimals().isEmpty()) {
                            GamemodelInstance.gameModel.getHerds().remove(h);
                            System.out.println("Herd was removed (empty).");
                        }
                        break;
                    }
                }
            }
        }
    }

    public void KillRanger(Ranger target) {
    }

    public void update(float deltaTime) {
        movementTimer += deltaTime;

        boolean actionsInProgress = false;
        if (this.getActions() != null && this.getActions().size > 0) {
            actionsInProgress = true;
        }

        if (!actionsInProgress) {
            isMoving = false;
        }

        checkForAnimalsToShoot();

        move();



    }

    private void checkForAnimalsToShoot() {
        Vector2 currentPos = new Vector2(position.getX(), position.getY());

        for (Herd h : GamemodelInstance.gameModel.getHerds()) {
            Iterator<AnimalImpl> iterator = h.getAnimals().iterator();
            while (iterator.hasNext()) {
                Animal animal = iterator.next();
                Vector2 animalPos = new Vector2(animal.getPosition().getX(), animal.getPosition().getY());
                if (currentPos.dst(animalPos) <= shootRange) {
                    KillAnimal(animal);
                    return;
                }
            }
        }
    }

    @Override
    public void move() {
        if (!isAlive) return;

        this.clearActions();

        Vector2 currentPos = new Vector2(position.getX(), position.getY());

        float mapWidth = GamemodelInstance.gameModel.getMapWidth();
        float mapHeight = GamemodelInstance.gameModel.getMapHeight();

        float moveDistance = MathUtils.random(50, 200);
        float randomAngle = MathUtils.random(0, MathUtils.PI2);

        Vector2 randomTarget = new Vector2(
            currentPos.x + moveDistance * MathUtils.cos(randomAngle),
            currentPos.y + moveDistance * MathUtils.sin(randomAngle)
        );

        randomTarget.x = MathUtils.clamp(randomTarget.x, 0, mapWidth);
        randomTarget.y = MathUtils.clamp(randomTarget.y, 0, mapHeight);

        Array<Action> actions = createMoveToActions(speed, currentPos, randomTarget);
        if (actions.size > 0) {
            for (Action action : actions) {
                this.addAction(action);
            }
            isMoving = true;
        }
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getShootRange() {
        return shootRange;
    }

    public void setShootRange(int range) {
        this.shootRange = range;
    }
}
