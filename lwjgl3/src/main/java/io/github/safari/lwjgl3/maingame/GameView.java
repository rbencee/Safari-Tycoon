package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.positionable.visitors.Tourist;
import org.lwjgl.opengl.GL20;


public class GameView implements Screen {

    private Skin skin;

    private Stage gameStage;
    private Stage uiStage;
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

    private OrthographicCamera minimapCamera;
    private FitViewport minimapViewport;
    private SpriteBatch minimapBatch;


    private final Texture treeTexture;
    private final Texture lakeTexture;
    private final Texture grassTexture;
    private final Texture bushTexture;

    private final SpriteBatch spriteBatch;

    private ScreenViewport viewport;
    private final float cameraMaxZoom = 1.5f;
    private final float cameraMinZoom = 0.5f;
    private final float targetZoom = 0.5f;

    private static final float MINIMAP_SCALE = 0.2f;
    private static final int MINIMAP_SIZE = (int) (3200 * MINIMAP_SCALE);
    private static final int MINIMAP_BORDER = 20;
    private ShapeRenderer shapeRenderer;

    private static final float EDGE_MARGIN = 20f;       // Aktiválódási zóna széleknél
    private static final float CAMERA_SPEED = 300f;     // Pixel per másodperc

    private boolean isDragging = false;

    private FrameBuffer fogBuffer;
    private Texture fogTexture;
    private SpriteBatch fogBatch;
    private boolean minimapVisible = false;


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

        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.minimapBatch = new SpriteBatch();
        this.game = game;
        this.gameModel = new GameModel(difficulty);
        fogBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        fogBatch = new SpriteBatch();
    }


    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new ScreenViewport();

        minimapCamera = new OrthographicCamera();
        minimapViewport = new FitViewport(mapWidth * MINIMAP_SCALE, mapHeight * MINIMAP_SCALE, minimapCamera);
        minimapCamera.setToOrtho(false, mapWidth * MINIMAP_SCALE, mapHeight * MINIMAP_SCALE);

        camera.setToOrtho(false, 1920, 1080);


        uiStage = new Stage(new ScreenViewport());
        gameStage = new Stage(viewport);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(gameStage);

        Gdx.input.setInputProcessor(multiplexer);

        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        Table table = new Table();
        table.setFillParent(true);


        shop = new Shop(skin, uiStage, this.gameModel);

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

        //zoomContolButtons();
        zoomControlScroll();
        speedbutton();

        uiStage.addActor(openShopButton);
        uiStage.addActor(table);

        gameController = new GameController(shop,this.gameModel, this);
        this.scorePanel = new ScorePanel(skin, uiStage, gameModel);
        setupPlace();
        minimapInput();
    }

    @Override
    public void render(float delta) {
        handleEdgeScrolling(delta);
        cameraMovement();
        camera.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            minimapVisible = !minimapVisible;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        drawSprites(spriteBatch, 1, delta);
        spriteBatch.end();

        gameStage.act(delta);
        gameStage.draw();

        renderFogOfWar();

        uiStage.act(delta);
        uiStage.draw();

        scorePanel.updateScore();
        gameModel.Simulation(delta);


        if (minimapVisible) {
            renderMinimap(delta);
            renderMinimapFogOfWar();
        }
    }

    public void renderFogOfWar() {
        if (!gameModel.isDaytime()) return;


        if (fogBuffer == null || fogBuffer.getWidth() != Gdx.graphics.getWidth() ||
            fogBuffer.getHeight() != Gdx.graphics.getHeight()) {
            if (fogBuffer != null) fogBuffer.dispose();
            fogBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(), false);
        }


        fogBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Environment env : gameModel.getEnvironments()) {
            float centerX = env.getPosition().getX() + env.getPosition().getWidth()/2;
            float centerY = env.getPosition().getY() + env.getPosition().getHeight()/2;
            float radius = 200f;

            shapeRenderer.setColor(1, 1, 1, 1.0f);
            shapeRenderer.circle(centerX, centerY, radius);
        }

        shapeRenderer.end();
        fogBuffer.end();

        fogBatch.begin();
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        OrthographicCamera screenCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenCamera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        screenCamera.update();
        fogBatch.setProjectionMatrix(screenCamera.combined);

        fogTexture = fogBuffer.getColorBufferTexture();
        fogBatch.draw(fogTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
            0, 0, fogTexture.getWidth(), fogTexture.getHeight(), false, true);

        fogBatch.end();
    }


    public void renderMinimapFogOfWar() {
        if (!gameModel.isDaytime()) return;

        int minimapX = Gdx.graphics.getWidth() - MINIMAP_SIZE;
        int minimapY = MINIMAP_BORDER;

        if (fogBuffer == null || fogBuffer.getWidth() != MINIMAP_SIZE || fogBuffer.getHeight() != MINIMAP_SIZE) {
            if (fogBuffer != null) fogBuffer.dispose();
            fogBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, MINIMAP_SIZE, MINIMAP_SIZE, false);
        }

        float mapScale = MINIMAP_SIZE / (float)Math.max(mapWidth, mapHeight);

        fogBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(minimapCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Environment env : gameModel.getEnvironments()) {
            float centerX = (env.getPosition().getX() + env.getPosition().getWidth()/2) * mapScale;
            float centerY = (env.getPosition().getY() + env.getPosition().getHeight()/2) * mapScale;
            float radius = 200f * mapScale;

            shapeRenderer.setColor(1, 1, 1, 1.0f);
            shapeRenderer.circle(centerX, centerY, radius);
        }

        shapeRenderer.end();
        fogBuffer.end();

        Gdx.gl.glViewport(minimapX, minimapY, MINIMAP_SIZE, MINIMAP_SIZE);
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(minimapX, minimapY, MINIMAP_SIZE, MINIMAP_SIZE);

        fogBatch.begin();
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        fogBatch.setProjectionMatrix(minimapCamera.combined);
        fogTexture = fogBuffer.getColorBufferTexture();

        fogBatch.draw(fogTexture, 0, 0, mapWidth * mapScale, mapHeight * mapScale,
            0, 0, fogTexture.getWidth(), fogTexture.getHeight(), false, true);

        fogBatch.end();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void renderMinimap(float delta) {
        int minimapX = Gdx.graphics.getWidth() - MINIMAP_SIZE;
        int minimapY = MINIMAP_BORDER;

        Gdx.gl.glViewport(minimapX - MINIMAP_BORDER, minimapY - MINIMAP_BORDER,
            MINIMAP_SIZE + 2*MINIMAP_BORDER, MINIMAP_SIZE + 2*MINIMAP_BORDER);

        shapeRenderer.setProjectionMatrix(minimapCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(-MINIMAP_BORDER, -MINIMAP_BORDER,
            mapWidth * MINIMAP_SCALE + 2*MINIMAP_BORDER,
            mapHeight * MINIMAP_SCALE + 2*MINIMAP_BORDER);
        shapeRenderer.end();

        Gdx.gl.glViewport(minimapX, minimapY, MINIMAP_SIZE, MINIMAP_SIZE);
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(minimapX, minimapY, MINIMAP_SIZE, MINIMAP_SIZE);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float scale = MINIMAP_SIZE / (float)Math.max(mapWidth, mapHeight);

        minimapBatch.setProjectionMatrix(minimapCamera.combined);
        minimapBatch.begin();
        mapRenderer.setView(minimapCamera);
        mapRenderer.render();
        minimapBatch.end();

        minimapBatch.begin();
            drawSprites(minimapBatch,scale,delta);
        minimapBatch.end();


        shapeRenderer.setProjectionMatrix(minimapCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        float frameX = (camera.position.x - camera.viewportWidth * camera.zoom / 2) * scale;
        float frameY = (camera.position.y - camera.viewportHeight * camera.zoom / 2) * scale;
        float frameWidth = camera.viewportWidth * camera.zoom * scale;
        float frameHeight = camera.viewportHeight * camera.zoom * scale;

        shapeRenderer.rect(frameX, frameY, frameWidth, frameHeight);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void minimapInput() {
        uiStage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int minimapX = Gdx.graphics.getWidth() - MINIMAP_SIZE - MINIMAP_BORDER;
                int minimapY = MINIMAP_BORDER;

                if (x > minimapX && x < minimapX + MINIMAP_SIZE &&
                    y > minimapY && y < minimapY + MINIMAP_SIZE) {

                    float clickX = (x - minimapX) / MINIMAP_SIZE * mapWidth * MINIMAP_SCALE;
                    float clickY = ((y - minimapY) / MINIMAP_SIZE) * mapHeight * MINIMAP_SCALE;

                    camera.position.set(clickX / MINIMAP_SCALE, clickY / MINIMAP_SCALE, 0);

                    clampCameraPosition();
                    camera.update();

                    isDragging = true;
                    return true;
                }
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (isDragging) {
                    int minimapX = Gdx.graphics.getWidth() - MINIMAP_SIZE - MINIMAP_BORDER;
                    int minimapY = MINIMAP_BORDER;

                    if (x > minimapX && x < minimapX + MINIMAP_SIZE &&
                        y > minimapY && y < minimapY + MINIMAP_SIZE) {

                        float dragX = (x - minimapX) / MINIMAP_SIZE * mapWidth * MINIMAP_SCALE;
                        float dragY = ((y - minimapY)) / MINIMAP_SIZE * mapHeight * MINIMAP_SCALE;

                        camera.position.set(dragX / MINIMAP_SCALE, dragY / MINIMAP_SCALE, 0);

                        clampCameraPosition();
                        camera.update();
                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isDragging = false;
            }
        });
    }


    private void clampCameraPosition() {
        float halfViewportWidth = camera.viewportWidth * camera.zoom / 2;
        float halfViewportHeight = camera.viewportHeight * camera.zoom / 2;

        if (camera.position.x - halfViewportWidth < 0) {
            camera.position.x = halfViewportWidth;
        }

        if (camera.position.x + halfViewportWidth > mapWidth) {
            camera.position.x = mapWidth - halfViewportWidth;
        }

        if (camera.position.y - halfViewportHeight < 0) {
            camera.position.y = halfViewportHeight;
        }

        if (camera.position.y + halfViewportHeight > mapHeight) {
            camera.position.y = mapHeight - halfViewportHeight;
        }
    }

    private void handleEdgeScrolling(float delta) {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Vector3 move = new Vector3();

        if (mouseX < EDGE_MARGIN) {
            move.x -= CAMERA_SPEED * delta;
        } else if (mouseX > screenWidth - EDGE_MARGIN) {
            move.x += CAMERA_SPEED * delta;
        }

        if (mouseY > screenHeight - EDGE_MARGIN) {
            move.y -= CAMERA_SPEED * delta;
        } else if (mouseY < EDGE_MARGIN) {
            move.y += CAMERA_SPEED * delta;
        }

        camera.position.add(move);
        camera.update();
    }



    private void drawSprites(SpriteBatch spriteBatch, float scale, float delta) {
        for (Environment env : gameModel.getEnvironments()) {
            if (env instanceof Tree) {
                spriteBatch.draw(treeTexture, env.getPosition().getX() * scale, env.getPosition().getY() * scale, env.getPosition().getWidth() * scale, env.getPosition().getHeight() * scale);
            } else if (env instanceof Bush) {
                spriteBatch.draw(bushTexture, env.getPosition().getX() * scale, env.getPosition().getY() * scale, env.getPosition().getWidth() * scale, env.getPosition().getHeight() * scale);
            } else if (env instanceof Lake) {
                spriteBatch.draw(lakeTexture, env.getPosition().getX() * scale, env.getPosition().getY() * scale, env.getPosition().getWidth() * scale, env.getPosition().getHeight() * scale);
            } else if (env instanceof Grass) {
                spriteBatch.draw(grassTexture, env.getPosition().getX() * scale, env.getPosition().getY() * scale, env.getPosition().getWidth() * scale, env.getPosition().getHeight() * scale);
            }
        }


        for (Road road : gameModel.getRoads()) {
            spriteBatch.draw(road.getTexture(), road.getPosition().getX() * scale, road.getPosition().getY() * scale, road.getPosition().getWidth() * scale, road.getPosition().getHeight() * scale);

        }
        for (Herd herd : gameModel.getHerds()) {
            for (Animal animal : herd.getAnimals()) {
                spriteBatch.draw(animal.getTexture(), animal.getPosition().getX() * scale, animal.getPosition().getY() * scale, animal.getPosition().getWidth() * scale, animal.getPosition().getHeight() * scale);
            }
        }

        for (Jeep jeep : gameModel.getJeeps()) {
            spriteBatch.draw(jeep.getTexture(),
                jeep.getPosition().getX() * scale,
                jeep.getPosition().getY() * scale,
                jeep.getPosition().getWidth() * scale,
                jeep.getPosition().getHeight() * scale);
        }

        for (Tourist tourist : gameModel.getTourists()) {
            spriteBatch.draw(tourist.getTexture(),
                tourist.getPosition().getX() * scale,
                tourist.getPosition().getY() * scale,
                tourist.getPosition().getWidth() * scale,
                tourist.getPosition().getHeight() * scale);
        }

    }

    /*
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

        uiStage.addActor(zoomLabel);
        uiStage.addActor(zoomOutButton);
        uiStage.addActor(zoomInButton);
    }
    */

    private void zoomControlScroll() {
        InputListener scrollListener = new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                float newZoom = camera.zoom + (amountY * targetZoom);
                newZoom = Math.max(cameraMinZoom, Math.min(cameraMaxZoom, newZoom));
                camera.zoom += (newZoom - camera.zoom) * 0.2f;

                clampCameraPosition();
                camera.update();
                return true;
            }
        };

        gameStage.addListener(scrollListener);
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


        uiStage.addActor(daySpeedButton);
        uiStage.addActor(weekSpeedButton);
        uiStage.addActor(monthSpeedButton);
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

        clampCameraPosition();

        camera.update();

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.setToOrtho(false, width, height);

        uiStage.getViewport().update(width, height, true);
        gameStage.getViewport().update(width, height, true);

        int newMinimapSize = (int) (MINIMAP_SIZE * (width / 1920f));
        minimapViewport.update(newMinimapSize, newMinimapSize);
        camera.update();
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
        gameStage.dispose();
        uiStage.dispose();
        skin.dispose();
        spriteBatch.dispose();
        minimapBatch.dispose();
        shapeRenderer.dispose();
        treeTexture.dispose();
        lakeTexture.dispose();
        grassTexture.dispose();
        bushTexture.dispose();
    }


    public Stage getGameStage() {
        return gameStage;
    }

    public Stage getUiStage() {
        return uiStage;
    }

    private void setupPlace()
    {
        uiStage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = uiStage.hit(x, y, true);
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

                    if (item.getName().equals("Jeep")) isjeep = true;


                    boolean placed = gameController.TryToPlace(world.x -32 , world.y - 32, 64, 64, 0, 0, isjeep);
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
                    float roundedX = (float)Math.floor(world.x / gridSize) * gridSize ;
                    float roundedY = (float)Math.floor(world.y / gridSize) * gridSize;


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
