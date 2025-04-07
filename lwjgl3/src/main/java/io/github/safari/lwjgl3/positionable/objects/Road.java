package io.github.safari.lwjgl3.positionable.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class Road extends Environment implements Positionable {
    private Texture texture;



    public Road(Position position) {
        super(position);
        this.texture = createGrayTexture();
    }

    private Texture createGrayTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        Texture tex = new Texture(pixmap);
        pixmap.dispose();
        return tex;

    }

    public Texture getTexture() {
        return texture;
    }











}
