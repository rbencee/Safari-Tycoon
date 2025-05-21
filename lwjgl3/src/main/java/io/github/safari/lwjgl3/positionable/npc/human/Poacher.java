package io.github.safari.lwjgl3.positionable.npc.human;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.BehaviourHelper;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.positionable.visitors.Tourist;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Poacher extends Actor implements Human, Positionable {
    private int shootRange;
    private int visibilityRange;
    private Texture texture;
    private Position position;
    private int speed;
    private Random random;
    private float checkTimer;
    private static final float CHECK_INTERVAL = 5.0f;
    private float moveTimer;
    private static final float MOVE_INTERVAL = 5.0f;
    private boolean isVisible = false;


    public Poacher(Position position) {
        this.position = position;
        if(Gdx.app != null && Gdx.gl != null) {
            this.texture = new Texture("textures/humans/poacher.png");
        }
        this.shootRange = 300;
        this.visibilityRange = 500;
        this.speed = 40;
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

        checkTimer += delta * GamemodelInstance.gameModel.getTimeMultiplicator();
        moveTimer += delta;

        if (checkTimer >= CHECK_INTERVAL) {
            checkTimer = 0;
            checkForAnimalsToShoot();
            checkForRangersToShoot();
        }

        if (!hasActions()) {
            moveToRandomLocation();
        }

        updateVisibility();
    }

    public boolean getVisibility(){
        return isVisible;
    }

    public void updateVisibility(){
        isVisible = false;
        Vector2 poacherPos = new Vector2(position.getX(), position.getY());

        for (Ranger ranger : GamemodelInstance.gameModel.getRangers()) {
            Vector2 rangerPos = new Vector2(ranger.getPosition().getX(), ranger.getPosition().getY());
            if (poacherPos.dst(rangerPos) <= visibilityRange) {
                isVisible = true;
                return;
            }
        }

        for (Tourist tourist : GamemodelInstance.gameModel.getTourists()) {
            Vector2 touristPos = new Vector2(tourist.getPosition().getX(), tourist.getPosition().getY());
            if (poacherPos.dst(touristPos) <= visibilityRange) {
                isVisible = true;
                return;
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

    private void checkForRangersToShoot() {
        ArrayList<Ranger> rangers = GamemodelInstance.gameModel.getRangers();
        Vector2 poacherPos = new Vector2(position.getX(), position.getY());

        for (Ranger ranger : rangers) {
            Vector2 rangerPos = new Vector2(ranger.getPosition().getX(), ranger.getPosition().getY());
            float distance = poacherPos.dst(rangerPos);

            if (distance <= shootRange) {
                KillRanger(ranger);
                break;
            }
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
                        if(Gdx.app != null) {
                            Gdx.app.log("Poacher", "Animal killed: " + target.getAnimalSpecies());
                        }
                        break;
                    }
                }
            }
        }
    }

    public void KillRanger(Ranger target) {
        if (target != null) {
            Vector2 poacherPos = new Vector2(position.getX(), position.getY());
            Vector2 rangerPos = new Vector2(target.getPosition().getX(), target.getPosition().getY());

            float distance = poacherPos.dst(rangerPos);

            if (distance <= shootRange) {
                if (Math.random() < 0.5) {
                    ArrayList<Ranger> rangers = GamemodelInstance.gameModel.getRangers();
                    ArrayList<Ranger> tempList = new ArrayList<>(rangers);
                    boolean removed = tempList.remove(target);
                    if (removed) {
                        rangers.clear();
                        rangers.addAll(tempList);
                    }
                } else {
                    System.out.println("Poacher shot at Ranger but missed!");
                }
            }
        }
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
