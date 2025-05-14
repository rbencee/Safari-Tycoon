package io.github.safari.lwjgl3.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.safari.lwjgl3.maingame.ConstantCollector;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;

public class HerdFoodAndDrinkBar extends Actor {
    private Herd herd;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private OrthographicCamera camera;

    public HerdFoodAndDrinkBar(Herd herd, OrthographicCamera camera) {
        this.herd = herd;
        this.camera = camera;
    }
    //batch.draw(speciesData.textureRegion(), getX() * scale, getY() * scale, getPosition().getWidth() * scale, getPosition().getHeight() * scale); // simple draw


    public void render(){
        if (herd != null && !herd.getAnimals().isEmpty()) {

            float barWidth = ConstantCollector.BASE_ELEMENT_WIDTH;
            float barHeight = ConstantCollector.BASE_ELEMENT_HEIGHT / 8;

            float foodWidth = (float) (barWidth * (herd.getMinHunger() / 100));
            float drinkWidth = (float) (barWidth * (herd.getMinThirst() / 100));

            float barX = herd.getPosition().getX();
            float barY = herd.getPosition().getY() + barWidth;

            Vector3 foodBarPosition = camera.project(new Vector3(barX, barY, 0));
            Vector3 drinkBarPosition = camera.project(new Vector3(barX, barY + barHeight, 0));

            drawBar(foodWidth, barHeight, barWidth, Color.RED, foodBarPosition);
            drawBar(drinkWidth, barHeight, barWidth, Color.BLUE, drinkBarPosition);
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

    private void drawBar(float amount, float barHeight, float barWidth, Color color, Vector3 screenPosition){
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(screenPosition.x, screenPosition.y, barWidth, barHeight);
        shapeRenderer.end();

        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(screenPosition.x, screenPosition.y, amount, barHeight);  // Draw the foreground rectangle
        shapeRenderer.end();
    }
}
