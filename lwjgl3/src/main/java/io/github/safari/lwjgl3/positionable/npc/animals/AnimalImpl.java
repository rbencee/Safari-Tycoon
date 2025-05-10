package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalType;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.SpeciesData;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.SpeciesFactory;
import io.github.safari.lwjgl3.util.Positionable;

public class AnimalImpl extends Actor implements Animal, Positionable {
    private final SpeciesData speciesData;
    double age;
    double hunger;
    double thirst;
    Position position;
    final AnimalSpecies animalSpecies;
    boolean toRemove = false;

    public AnimalImpl(
        double age,
        double hunger,
        double thirst,
        Position position,
        AnimalSpecies animalSpecies) {

        this.speciesData = SpeciesFactory.getSpeciesData(animalSpecies);
        this.age = age;
        this.hunger = hunger;
        this.thirst = thirst;
        this.position = position;
        this.setPosition(position.getX(), position.getY()); //inherited from Actor
        this.animalSpecies = animalSpecies;
    }

    public void render(SpriteBatch batch, float scale) {
        batch.draw(speciesData.textureRegion(), getX() * scale, getY() * scale, getPosition().getWidth() * scale, getPosition().getHeight() * scale); // simple draw
    }

    @Override
    public float getVisionRange() {
        return speciesData.visionRange();
    }

    @Override
    public double getAge() {
        return age;
    }

    @Override
    public double getMaxAge() {
        return speciesData.maxAge();
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
        return speciesData.speed();
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
        return speciesData.animalType();
    }

    @Override
    public AnimalSpecies getAnimalSpecies() {
        return animalSpecies;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return speciesData.textureRegion();
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
