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
    final float visionRange;

    double age;
    final double maxAge;
    double hunger;
    double thirst;
    final double speed;
    final Texture texture;
    Position position;
    final AnimalSpecies animalSpecies;
    final Behaviour behaviour;
    final EdibleCollection edibles;

    public AnimalImpl (
        float visionRange,
        double age,
        double maxAge,
        double hunger,
        double thirst,
        double speed,
        Texture texture,
        Position position,
        AnimalSpecies animalSpecies,
        Behaviour behaviour, EdibleCollection edibles) {

        this.visionRange = visionRange;
        this.age = age;
        this.maxAge = maxAge;
        this.hunger = hunger;
        this.thirst = thirst;
        this.speed = speed;
        this.texture = texture;
        this.position = position;
        this.setPosition(position.getX(), position.getY()); //inherited from Actor
        this.animalSpecies = animalSpecies;
        this.behaviour = behaviour;
        this.edibles = edibles;
    }

    @Override
    public float getVisionRange() {
        return visionRange;
    }

    @Override
    public double getAge() {
        return age;
    }

    @Override
    public double getMaxAge() {
        return maxAge;
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
        return speed;
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

        if (getActions().isEmpty() && behaviour.shouldCreateNewAction(this)) {
            addAction(behaviour.createFittingAction(this));
            System.out.println("animalimpl: action added");
        }

    }

    //todo detect water

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, this.getX(), this.getY());
    }
}
