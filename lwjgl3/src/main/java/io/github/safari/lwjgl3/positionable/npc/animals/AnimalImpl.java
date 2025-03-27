package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    //??? picture;
    final Texture texture;
    Position position;
    ArrayList<Position> knownFood;
    ArrayList<Position> knownWater;
    final AnimalSpecies animalSpecies;
    final Behaviour behaviour;

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
        Behaviour behaviour) {


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
        return position;
    }

    @Override
    public ArrayList<Position> getKnownFood() {
        return knownFood;
    }

    @Override
    public ArrayList<Position> getKnownWater() {
        return knownWater;
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

    //todo detect water

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(!this.hasActions() || behaviour.shouldCreateNewAction(this)){
            this.clearActions();
            Action b = behaviour.createFittingAction(this);
            if (b != null) {
                this.addAction(b);
            }
        }
    }
}
