package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import org.lwjgl.opengl.GL20;



public class GameView implements Screen {

    private Skin skin;
    private Stage stage;
    GameModel gameModel;
    private Game game;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;



    public GameView(Game game, int difficulty)
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        map = new TmxMapLoader().load("untitled.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        this.game = game;
        this.gameModel = new GameModel(difficulty);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        stage.addActor(AnimalFactory.createCapybara(new Position(0,0)));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        Table table = new Table();
        table.setFillParent(true);


        //table.add(title).pad(20);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            camera.translate(-5, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            camera.translate(5, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            camera.translate(0, 5);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            camera.translate(0, -5);
        }

        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();


        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,width,height);
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
