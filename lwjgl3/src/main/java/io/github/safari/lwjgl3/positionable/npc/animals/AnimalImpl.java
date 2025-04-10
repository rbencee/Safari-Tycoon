package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.util.Positionable;

import java.util.ArrayList;

public class AnimalImpl extends Actor implements Animal, Positionable {
    double age;
    double hunger;
    double thirst;
    final Texture texture;
    Position position;
    final AnimalSpecies animalSpecies;
    final Behaviour behaviour;
    final EdibleCollection edibles;

    public AnimalImpl (
        double age,
        double hunger,
        double thirst,
        Texture texture,
        Position position,
        AnimalSpecies animalSpecies,
        Behaviour behaviour,
        EdibleCollection edibles) {

        this.age = age;
        this.hunger = hunger;
        this.thirst = thirst;
        this.texture = texture;
        this.position = position;
        this.setPosition(position.getX(), position.getY()); //inherited from Actor
        this.animalSpecies = animalSpecies;
        this.behaviour = behaviour;
        this.edibles = edibles;
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
    public void act(float delta) {
        super.act(delta);
        behaviour.detectFood(this, edibles);
        behaviour.detectWater(this, edibles);

        if (getActions().isEmpty()) {
            addAction(behaviour.createFittingAction(this));
        }
        this.hunger -= delta;
        this.thirst -= delta;
        //todo egyen is, ha odaért
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, this.getX(), this.getY());
    }
}
