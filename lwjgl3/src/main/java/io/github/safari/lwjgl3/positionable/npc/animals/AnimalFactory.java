package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.HerbivoreBehaviour;

public class AnimalFactory {
    public static AnimalImpl createCapybara(Position position){
        return new AnimalImpl(30f, 0, 10, 100, 100, 3, createTextureRegion("animals/capybara.png"), position, AnimalSpecies.CAPYBARA, new HerbivoreBehaviour());
    }
    /*public static Animal createMammoth(Position position){
        return new AnimalImpl(30f, 0, 30, 100, 100, 5, position, AnimalSpecies.MAMMOTH, new HerbivoreBehaviour());
    }
    public static Animal createLion(Position position){
        return new AnimalImpl(30f, 0, 10, 100, 100, 6, position, AnimalSpecies.LION, new PredatorBehaviour());
    }
    public static Animal createDinosaur(Position position){
        return new AnimalImpl(30f, 0, 10, 100, 100, 4, position, AnimalSpecies.DINOSAUR, new PredatorBehaviour());
    }*/

    private static TextureRegion createTextureRegion(String path){
        Texture texture = new Texture(Gdx.files.internal(path));
        return new TextureRegion(texture, 50, 50);
    }
}
