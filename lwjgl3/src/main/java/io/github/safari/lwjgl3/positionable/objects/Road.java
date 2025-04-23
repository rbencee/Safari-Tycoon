package io.github.safari.lwjgl3.positionable.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.util.Positionable;

public class Road extends Environment implements Positionable {
    private Texture texture;
    private int roadtype; //1--3




    public Road(Position position) {
        this(position, 1);
    }

    public Road(Position position, int roadtype) {
        super(position);
        this.texture = createGrayTexture(roadtype);
    }

    public Road(Position position, int roadtype, Texture texture) {
        super(position);
        this.texture = texture;

    }

    private Texture createGrayTexture(int roadtype) {


        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        if (roadtype == 2) {
            pixmap.setColor(Color.GREEN);
        } else if (roadtype == 3) {
            pixmap.setColor(Color.RED);
        } else {
            pixmap.setColor(Color.GRAY);
        }
        this.roadtype = roadtype;

        pixmap.fill();
        Texture tex = new Texture(pixmap);
        pixmap.dispose();
        return tex;

    }

    public int getRoadtype() {
        return roadtype;
    }

    public Texture getTexture() {
        return texture;
    }

}
