package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class AnimalImpl extends Actor implements Animal, Positionable {
    double age;
    double hunger;
    double thirst;
    final Texture texture;
    Position position;
    final AnimalSpecies animalSpecies;
    boolean toRemove = false;

    public AnimalImpl(
        double age,
        double hunger,
        double thirst,
        Texture texture,
        Position position,
        AnimalSpecies animalSpecies) {

        this.age = age;
        this.hunger = hunger;
        this.thirst = thirst;
        this.texture = texture;
        this.position = position;
        this.setPosition(position.getX(), position.getY()); //inherited from Actor
        this.animalSpecies = animalSpecies;
    }

    @Override
    public float getVisionRange() {
        return animalSpecies.getVisionRange();
    }

    @Override
    public double getAge() {
        return age;
    }

    @Override
    public double getMaxAge() {
        return animalSpecies.getMaxAge();
    }

    @Override
    public double getHunger() {
        return hunger;
    }

    @Override
    public double getThirst() {
        return thirst;
    }

    @Override
    public double getSpeed() {
        return animalSpecies.getSpeed();
    }

    public boolean isToRemove() {
        return toRemove;
    }

    @Override
    public Position getPosition() {
        return new Position(this.getX(), this.getY(), this.position.getWidth(), this.position.getHeight());
    }

    @Override
    public AnimalType getAnimalType() {
        return animalSpecies.getAnimalType();
    }

    @Override
    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void eat() {
        this.hunger = 100;
    }

    @Override
    public void drink() {
        this.thirst = 100;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.age += delta * GamemodelInstance.gameModel.getTimeMultiplicator();
        this.hunger -= delta * GamemodelInstance.gameModel.getTimeMultiplicator() * (1 + age / getMaxAge());
        this.thirst -= delta * GamemodelInstance.gameModel.getTimeMultiplicator() * (1 + age / getMaxAge());

        if (age > getMaxAge() || thirst < 0 || hunger < 0) {
            toRemove = true;
            this.remove();
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
