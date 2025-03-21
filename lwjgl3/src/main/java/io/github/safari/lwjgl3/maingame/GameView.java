package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.safari.lwjgl3.positionable.objects.*;
import org.lwjgl.opengl.GL20;


public class GameView implements Screen {

    private Skin skin;

    private Stage stage;
    private GameModel gameModel;
    private Game game;

    private int mapWidth;
    private int mapHeight;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private float cameraSpeed;
    private OrthographicCamera camera;


    private Texture treeTexture;
    private Texture lakeTexture;
    private Texture grassTexture;
    private Texture bushTexture;

    private SpriteBatch spriteBatch;

    private FitViewport viewport;



    public GameView(Game game, int difficulty)
    {

        map = new TmxMapLoader().load("savannasmallsmall.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);



        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;
        cameraSpeed = 3f;


        treeTexture = new Texture("textures/bush2.png");
        lakeTexture = new Texture("textures/bush2.png");
        grassTexture = new Texture("textures/bush2.png");
        bushTexture = new Texture("textures/bush2.png");

        this.spriteBatch = new SpriteBatch();
        this.game = game;
        this.gameModel = new GameModel(difficulty);
    }


    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        camera.setToOrtho(false, 1920, 1080);

        // camera.zoom = 0.75f;
        camera.zoom = 0.5f;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        Table table = new Table();
        table.setFillParent(true);


        //table.add(title).pad(20);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        cameraMovement();

        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        for (Environment env : gameModel.getEnvironments()) {
            if (env instanceof Tree) {
                spriteBatch.draw(treeTexture, env.getX(), env.getY(), 32, 32);
            } else if (env instanceof Bush) {
                spriteBatch.draw(bushTexture, env.getX(), env.getY(), 32, 32);
            } else if (env instanceof Lake) {
                spriteBatch.draw(lakeTexture, env.getX(), env.getY(), 32, 32);
            } else if (env instanceof Grass) {
                spriteBatch.draw(grassTexture, env.getX(), env.getY(), 32, 32);
            }
        }
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    private void cameraMovement(){
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            camera.translate(-cameraSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            camera.translate(cameraSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            camera.translate(0, cameraSpeed);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            camera.translate(0, -cameraSpeed);
        }

        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;

        if (camera.position.x - halfWidth < 0) {
            camera.position.x = halfWidth;
        }
        if (camera.position.x + halfWidth > mapWidth) {
            camera.position.x = mapWidth - halfWidth;
        }
        if (camera.position.y - halfHeight < 0) {
            camera.position.y = halfHeight;
        }
        if (camera.position.y + halfHeight > mapHeight) {
            camera.position.y = mapHeight - halfHeight;
        }

        camera.update();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        stage.dispose();
        skin.dispose();
    }
}
