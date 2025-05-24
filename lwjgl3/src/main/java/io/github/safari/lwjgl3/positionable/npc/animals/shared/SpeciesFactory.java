package io.github.safari.lwjgl3.positionable.npc.animals.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public class SpeciesFactory {
    public static final Map<AnimalSpecies, SpeciesData> speciesCache = new HashMap<>();
    public static boolean loaded = false;

    public static void loadData() {
        if (loaded) return;
        Array<SpeciesConfig> speciesList = new Array<>();

        try {
            Json json = new Json();
            if(Gdx.app != null) {
                speciesList = json.fromJson(Array.class, SpeciesConfig.class, Gdx.files.internal("species.json"));
                Gdx.app.log("SpeciesFactory", "Loaded " + speciesList.size + " species from JSON");
            }
        } catch (Exception e) {
            if(Gdx.app != null) {
                Gdx.app.error("SpeciesFactory", "Failed to load species.json", e);
            }
        }

        for (SpeciesConfig config : speciesList) {
            AnimalSpecies animalSpecies = AnimalSpecies.valueOf(String.valueOf(config.animalSpecies));
            AnimalType animalType = AnimalType.valueOf(String.valueOf(config.animalType));
            TextureRegion region = null;

            if(Gdx.app != null && Gdx.gl != null) {
                Texture texture = new Texture("textures/animals/" + config.texture);
                region = new TextureRegion(texture);
            }

            SpeciesData data = new SpeciesData(animalType, animalSpecies, config.maxAge, config.speed, config.visionRange, config.reproductionTime, region);

            speciesCache.put(animalSpecies, data);
        }
        loaded = true;
    }

    public static SpeciesData getSpeciesData(AnimalSpecies species) {
        loadData();
        return speciesCache.get(species);
    }

    public static void disposeAll() {
        for (SpeciesData data : speciesCache.values()) {
            data.textureRegion().getTexture().dispose();
        }
        speciesCache.clear();
        loaded = false;
    }
}
