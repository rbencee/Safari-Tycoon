package io.github.safari.lwjgl3.positionable.npc.animals.shared;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public record SpeciesData(AnimalType animalType,
                          AnimalSpecies animalSpecies,
                          double maxAge,
                          float speed,
                          float visionRange,
                          int reproductionTime,
                          TextureRegion textureRegion) {
}
