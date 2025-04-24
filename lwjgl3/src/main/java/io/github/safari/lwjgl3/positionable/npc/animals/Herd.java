package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.CloneableAction;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.CloneableMoveToAction;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.KillAction;
import io.github.safari.lwjgl3.positionable.npc.animals.actions.SleepAction;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.BehaviourHelper;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.RandomMovingBehaviour;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Herd extends Group implements Positionable {
    private final AnimalSpecies animalSpecies;
    private final ArrayList<AnimalImpl> animals = new ArrayList<>();
    private final ArrayList<Behaviour> behaviours;
    private Random random = new Random();
    private boolean isMovingRandomly = false;


    public Herd(AnimalSpecies animalSpecies, ArrayList<Behaviour> behaviours) {
        this.animalSpecies = animalSpecies;
        this.behaviours = behaviours;


    }

    public void addToHerd(AnimalImpl animal) {
        animals.add(animal);
        addActor(animal);
    }

    public void ChooseOneObjective() {

    }

    public ArrayList<AnimalImpl> getAnimals() {
        return animals;
    }

    public AnimalType getAnimalType() {
        return animalSpecies.getAnimalType();
    }

    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
    }

    public int animalCount() {
        return animals.size();
    }

    @Override
    public Position getPosition() {
        return animals.get(0).getPosition();
    }

    public double getMinHunger() {
        double minHunger = Integer.MAX_VALUE;
        for (AnimalImpl animal : animals) {
            if (animal.getHunger() < minHunger) {
                minHunger = animal.getHunger();
            }
        }
        return minHunger;
    }

    public double getMinThirst() {
        double min = Integer.MAX_VALUE;
        for (AnimalImpl animal : animals) {
            if (animal.getThirst() < min) {
                min = animal.getThirst();
            }
        }
        return min;
    }

    public float getVisionRange() {
        return animals.get(0).getVisionRange();
    }

    private float checkEatTimer = 0;

    private float checkMovingRandomlyTimer = 0;

    @Override
    public void act(float delta) {
        super.act(delta);

        if (animals.isEmpty()) {
            remove();
            GamemodelInstance.gameModel.getHerds().remove(this);
            if (animalSpecies.getAnimalType().equals(AnimalType.HERBIVORE)) {
                GamemodelInstance.gameModel.getAllHerbivores().remove(this);
            }
        }

        checkMovingRandomlyTimer += delta;
        if (isMovingRandomly && checkMovingRandomlyTimer > 2) {
            if (getMinThirst() < 30 || getMinHunger() < 30) {
                animals.forEach(Actor::clearActions);
                isMovingRandomly = false;
            }
        }

        checkEatTimer += delta;
        if (this.animalSpecies.getAnimalType().equals(AnimalType.PREDATOR) && this.getMinHunger() < 30 && checkEatTimer > 2) {
            Herd herd = isHerbivoreNearby();
            boolean doesNotHaveKillAction = true;
            Array<Action> actions = animals.get(0).getActions();
            for (Action action : actions) {
                if (action instanceof AfterAction && ((AfterAction) action).getAction() instanceof KillAction) {
                    doesNotHaveKillAction = false;
                }
            }

            if (herd != null && doesNotHaveKillAction) {
                animals.forEach(Actor::clearActions);

                Array<Action> newActions = BehaviourHelper.createMoveToActions(
                    animalSpecies.getSpeed(),
                    new Vector2(getPosition().getX(), getPosition().getY()),
                    new Vector2(herd.getPosition().getX(), herd.getPosition().getY()));

                for (Action action : newActions) {
                    if (action instanceof CloneableMoveToAction moveToAction) {
                        animals.forEach(actor -> actor.addAction(Actions.after(moveToAction.clone())));
                    }
                }

                animals.forEach(actor -> {
                    actor.addAction(Actions.after(new KillAction(herd)));
                    actor.addAction(new SleepAction());
                });
            } else if (herd == null && doesNotHaveKillAction) {
                clearActions();
                actAsBehaviours();
            }
            checkEatTimer = 0;
        }


        List<AnimalImpl> toDelete = animals.stream()
            .filter(AnimalImpl::isToRemove)
            .toList();

        animals.removeAll(toDelete);

        if (animals.isEmpty()) {
            GamemodelInstance.gameModel.getHerds().remove(this);
            this.remove();
            return;
        }

        reproduce(delta);

        joinHerdsIfPossible();


        for (AnimalImpl animal : animals) {
            if (animal.hasActions()) {
                return;
            }
        }

        actAsBehaviours();
    }

    private void actAsBehaviours() {
        for (Behaviour behaviour : behaviours) {
            behaviour.doRepeatedly(this);

            if (behaviour.canCreateAction(this)) {
                if (behaviour instanceof RandomMovingBehaviour) {
                    this.isMovingRandomly = true;
                }
                Array<Action> actions = behaviour.createActions(this);
                for (Actor child : getChildren()) {
                    for (Action action : actions) {
                        if (!(action instanceof CloneableAction)) {
                            throw new RuntimeException("Not instance of CloneableAction");
                        }
                        child.addAction(Actions.after(((CloneableAction) action).clone()));
                    }
                }
                return;
            }
        }
    }

    private float deltaCounter = 0;

    private void reproduce(float delta) {
        deltaCounter += delta;

        if (deltaCounter * GamemodelInstance.gameModel.getTimeMultiplicator() > animalSpecies.getReproductionTime()) {
            deltaCounter = 0;
            long adults = animals.stream().filter(animal -> animal.getAge() / animal.getMaxAge() > 0.2).count();
            if (adults <= 1) {
                return;
            }

            for (int i = 0; i < adults / 2; i++) {
                if (random.nextInt(100) <= 30) {
                    AnimalImpl animal = createNewAnimal();
                    addToHerd(animal);
                    for (Action action : animals.get(0).getActions()) {
                        if (action instanceof AfterAction afterAction) {
                            if (!(afterAction.getAction() instanceof CloneableAction cloneableAction)) {
                                throw new RuntimeException("No CloneableAction");
                            }
                            animal.addAction(Actions.after(cloneableAction.clone()));
                        }
                    }
                }
            }
        }


    }

    private Herd isHerbivoreNearby() {
        List<Herd> preys = GamemodelInstance.gameModel.getAllHerbivores();
        for (Herd prey : preys) {

            if (prey.getAnimals().isEmpty()) {
                return null;
            }
            if (Position.distance(this.getPosition(), prey.getPosition()) <= this.getVisionRange()) {
                return prey;
            }
        }
        return null;
    }

    private void joinHerdsIfPossible() {
        ArrayList<Herd> herdsToRemove = new ArrayList<>();
        for (Herd herd : GamemodelInstance.gameModel.getHerds()) {
            if (herd.equals(this) || herdsToRemove.contains(herd)) continue;

            if (herd.getAnimalSpecies().equals(animalSpecies)) {
                if (Position.distance(getPosition(), herd.getPosition()) <= animals.get(0).getVisionRange()) {
                    herdsToRemove.add(herd);

                    for (AnimalImpl animal : herd.getAnimals()) {
                        addToHerd(animal);
                    }
                    herd.getAnimals().clear();

                    herd.remove();

                    for (Actor actor : this.getChildren()) {
                        actor.clearActions();
                    }
                }
            }
        }
        GamemodelInstance.gameModel.getHerds().removeAll(herdsToRemove);
    }

    private AnimalImpl createNewAnimal() {
        return new AnimalImpl(0, 100, 100, animals.get(0).getTexture(), this.getPosition(), this.getAnimalSpecies());
    }
}
