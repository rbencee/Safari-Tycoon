package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.HerbivoreBehaviour;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.PredatorBehaviour;

public class AnimalFactory {
    public static GameModel gameModel;
    public static Animal createCapybara(Position position){
        return new AnimalImpl(0, 40, 80, createTexture("textures/animals/capybara.png"), position, AnimalSpecies.CAPYBARA, new HerbivoreBehaviour(), gameModel);
    }
    /*public static Animal createMammoth(Position position){
        return new AnimalImpl(30f, 0, 30, 100, 100, 5, position, AnimalSpecies.MAMMOTH, new HerbivoreBehaviour());
    }
    public static Animal createLion(Position position){
        return new AnimalImpl(30f, 0, 10, 100, 100, 6, position, AnimalSpecies.LION, new PredatorBehaviour());
    }*/
    public static Animal createDinosaur(Position position){
        return new AnimalImpl(0, 100, 100, createTexture("textures/animals/dino.png"), position, AnimalSpecies.DINOSAUR, new PredatorBehaviour(), gameModel);
    }

    private static Texture createTexture(String path){
        Pixmap pixmap1 = new Pixmap(Gdx.files.internal(path));
        Pixmap pixmap2 = new Pixmap(30, 30, pixmap1.getFormat());
        pixmap2.drawPixmap(pixmap1,
            0, 0, pixmap1.getWidth(), pixmap1.getHeight(),
            0, 0, pixmap2.getWidth(), pixmap2.getHeight()
        );
        Texture texture = new Texture(pixmap2);
        pixmap1.dispose();
        pixmap2.dispose();
        return texture;
    }
}
