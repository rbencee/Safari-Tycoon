package io.github.safari.lwjgl3.positionable.npc.animals;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.SpeciesFactory;

public class AnimalFactory {
    public static Animal createNew(AnimalSpecies species, Position position) {
        return new AnimalImpl(0, 100, 100, position, species);
    }
}
