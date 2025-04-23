package io.github.safari.lwjgl3.positionable.npc.human;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.maingame.*;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.CloneableMoveToAction;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.BehaviourHelper;
import io.github.safari.lwjgl3.util.Positionable;
import io.github.safari.lwjgl3.util.pathfinding.PathFinderHelper;

import java.util.Iterator;
import java.util.Random;

public class Poacher extends Actor implements Human, Positionable {
    private int shootRange;
    private Texture texture;
    private Position position;
    private int speed;
    private Random random;
    private float checkTimer;
    private static final float CHECK_INTERVAL = 5.0f;
    private float moveTimer;
    private static final float MOVE_INTERVAL = 5.0f;

    public Poacher(Position position) {
        this.position = position;
        this.texture = new Texture("textures/humans/tourist.png");
        this.shootRange = 400;
        this.speed = 5;
        this.random = new Random();
        this.checkTimer = 0;
        this.moveTimer = 0;

        setPosition(position.getX(), position.getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.position.setX((int) getX());
        this.position.setY((int) getY());

        checkTimer += delta;
        moveTimer += delta;

        if (checkTimer >= CHECK_INTERVAL) {
            checkTimer = 0;
            checkForAnimalsToShoot();
        }

        if (moveTimer >= MOVE_INTERVAL || !hasActions()) {
            moveTimer = 0;
            moveToRandomLocation();
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

    public int getShootRange() {
        return shootRange;
    }

    public void setShootRange(int range) {
        this.shootRange = range;
    }
}
