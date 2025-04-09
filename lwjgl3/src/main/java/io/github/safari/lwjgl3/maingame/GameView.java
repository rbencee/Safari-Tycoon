package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import org.lwjgl.opengl.GL20;


public class GameView implements Screen {

    private Skin skin;

    private Stage stage;
    private final GameModel gameModel;
    private Shop shop;
    private final Game game;
    private GameController gameController;
    private ScorePanel scorePanel;

    private boolean isDraggingRoad = false;
    private Vector3 lastPlacedPos = new Vector3();

    private final int mapWidth;
    private final int mapHeight;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;

    private final float cameraSpeed;
    private OrthographicCamera camera;


    private final Texture treeTexture;
    private final Texture lakeTexture;
    private final Texture grassTexture;
    private final Texture bushTexture;
    private final Texture animaltexture;

    private SpriteBatch spriteBatch;

    private ScreenViewport viewport;
    private float cameraMaxZoom = 1.4f;
    private float cameraMinZoom = 0.6f;


    public GameView(Game game, int difficulty) {

        map = new TmxMapLoader().load("savannasmallsmall.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);


        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;
        cameraSpeed = 3f;


        treeTexture = new Texture("textures/tree3.png");
        lakeTexture = new Texture("textures/lake1.png");
        grassTexture = new Texture("textures/grass4.png");
        bushTexture = new Texture("textures/bush2.png");
        animaltexture = new Texture("textures/bush2.png");


        this.spriteBatch = new SpriteBatch();
        this.game = game;
        this.gameModel = new GameModel(difficulty);
    }


    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        camera.setToOrtho(false, 1920, 1080);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        Table table = new Table();
        table.setFillParent(true);


        shop = new Shop(skin, stage, this.gameModel);


        TextButton openShopButton = new TextButton("Shop", skin);
        openShopButton.setPosition(0, 0);
        openShopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (shop.isVisible()) {
                    shop.hide();
                } else {
                    shop.show();
                }
            }
        });

        zoomContolButtons();
        speedbutton();

        stage.addActor(openShopButton);
        stage.addActor(table);

        gameController = new GameController(shop, this.gameModel);
        this.scorePanel = new ScorePanel(skin, stage, gameModel);
        setupPlace();
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
                spriteBatch.draw(treeTexture, env.getPosition().getX(), env.getPosition().getY(), env.getPosition().getWidth(), env.getPosition().getHeight());
            } else if (env instanceof Bush) {
                spriteBatch.draw(bushTexture, env.getPosition().getX(), env.getPosition().getY(), env.getPosition().getWidth(), env.getPosition().getHeight());
            } else if (env instanceof Lake) {
                spriteBatch.draw(lakeTexture, env.getPosition().getX(), env.getPosition().getY(), env.getPosition().getWidth(), env.getPosition().getHeight());
            } else if (env instanceof Grass) {
                spriteBatch.draw(grassTexture, env.getPosition().getX(), env.getPosition().getY(), env.getPosition().getWidth(), env.getPosition().getHeight());
            }
        }


        for(Road road : gameModel.getRoads())
        {
            spriteBatch.draw(road.getTexture(), road.getPosition().getX(), road.getPosition().getY(), road.getPosition().getWidth(), road.getPosition().getHeight());

        }
        for (Herd herd : gameModel.getHerds()) {
            for (Animal animal : herd.getAnimals()) {
                spriteBatch.draw(animal.getTexture(), animal.getPosition().getX(), animal.getPosition().getY(), animal.getPosition().getWidth(), animal.getPosition().getHeight());

            }
        }

        for(Jeep jeep : gameModel.getJeeps())
        {
            spriteBatch.draw(jeep.getTexture(),
                jeep.getPosition().getX(),
                jeep.getPosition().getY(),
                jeep.getPosition().getWidth(),
                jeep.getPosition().getHeight());

        }
        spriteBatch.end();


        stage.act(delta);
        stage.draw();

        scorePanel.updateScore();
        gameModel.Simulation(delta);
    }

    private void zoomContolButtons() {
        Label zoomLabel = new Label("Zoom", skin);
        zoomLabel.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 45);

        TextButton zoomInButton = new TextButton("+", skin);
        zoomInButton.setPosition(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50);
        zoomInButton.setSize(50, 50);
        zoomInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (camera.zoom > cameraMinZoom) {
                    camera.zoom -= 0.1f;
                }
            }
        });

        TextButton zoomOutButton = new TextButton("-", skin);
        zoomOutButton.setPosition(Gdx.graphics.getWidth() - 210, Gdx.graphics.getHeight() - 50);
        zoomOutButton.setSize(50, 50);
        zoomOutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (camera.zoom < cameraMaxZoom) {
                    camera.zoom += 0.1f;
                }
            }
        });

        stage.addActor(zoomLabel);
        stage.addActor(zoomOutButton);
        stage.addActor(zoomInButton);
    }

    private void speedbutton() {
        float buttonWidth = 110f;
        float buttonHeight = 60f;
        float initialX = 10f; // Kezdeti X pozíció
        float spacing = 10f;


        TextButton daySpeedButton = new TextButton("Day", skin);
        daySpeedButton.setPosition(initialX, 80);
        daySpeedButton.setSize(buttonWidth, buttonHeight);
        daySpeedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameModel.setSpeed(1);
            }
        });

        // Week gomb
        TextButton weekSpeedButton = new TextButton("Week", skin);
        weekSpeedButton.setPosition(initialX + buttonWidth + spacing, 80);
        weekSpeedButton.setSize(buttonWidth, buttonHeight);
        weekSpeedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameModel.setSpeed(2);
            }
        });

        // Month gomb
        TextButton monthSpeedButton = new TextButton("Month", skin);
        monthSpeedButton.setPosition(initialX + 2 * (buttonWidth + spacing), 80);
        monthSpeedButton.setSize(buttonWidth, buttonHeight);
        monthSpeedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameModel.setSpeed(3);
            }
        });


        stage.addActor(daySpeedButton);
        stage.addActor(weekSpeedButton);
        stage.addActor(monthSpeedButton);
    }


    private void cameraMovement() {
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

        float halfWidth = camera.viewportWidth / 2 * camera.zoom;
        float halfHeight = camera.viewportHeight / 2 * camera.zoom;


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


    private void setupPlace() {
        stage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = stage.hit(x, y, true);
                if (target != null) return false;

                ShopItem item = shop.getShopItems();
                if (item == null) return false;

                Vector3 world = camera.unproject(new Vector3(x, Gdx.graphics.getHeight() - y, 0));

                if (item.getName().equals("Road")) {
                    isDraggingRoad = true;
                    lastPlacedPos.set(-999, -999, 0); // reset
                    return true;
                } else {

                    boolean isjeep = false;

                    if(item.getName().equals("Jeep")) isjeep = true;

                    // Normál egy dara.bos elhelyezés
                    boolean placed = gameController.TryToPlace(world.x - 32 , world.y - 32, 64, 64, 0, 0, isjeep);
                    if (placed) {
                        System.out.println("Item placed at : " + world.x + ", " + world.y);
                        shop.clearSelection(); 
                    }
                    return true;
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (isDraggingRoad) {
                    Vector3 world = camera.unproject(new Vector3(x, Gdx.graphics.getHeight() - y, 0));

                    float gridSize = 64f;
                    float roundedX = Math.round(world.x / gridSize) * gridSize - gridSize / 2;
                    float roundedY = Math.round(world.y / gridSize) * gridSize - gridSize / 2;


                    if (Math.abs(lastPlacedPos.x - roundedX) >= gridSize || Math.abs(lastPlacedPos.y - roundedY) >= gridSize) {
                        boolean placed = gameController.TryToPlace(roundedX, roundedY, 64, 64, 0, 0,false);
                        if (placed) {
                            System.out.println("Road placed at : " + roundedX + ", " + roundedY);
                            lastPlacedPos.set(roundedX, roundedY, 0);
                        }
                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isDraggingRoad = false;
            }
        });


    }
}
