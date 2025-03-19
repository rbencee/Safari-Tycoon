package io.github.safari.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.safari.lwjgl3.util.ShopType;


public class Shop {


    private Stage stage;
    private Table table;
    private Skin skin;
    private Shop shop;


    private boolean CanBuyDrone;
    private ShopType PageShown;

    public Shop()
    {
        this.PageShown = ShopType.PLANTS;
        create();
    }



    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json")); // LibGDX alap UI skin
        shop = new Shop();

        table = new Table();
        table.setFillParent(true);

        Label titleLabel = new Label("Shop", skin);
        table.add(titleLabel).padBottom(20).row();

        TextButton plantsButton = new TextButton("Plants", skin);
        TextButton animalsButton = new TextButton("Animals", skin);
        TextButton othersButton = new TextButton("Others", skin);

        plantsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shop.ShowPlantsPage();
            }
        });

        animalsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shop.ShowAnimalsPage();
            }
        });

        othersButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shop.ShowOthersPage();
            }
        });

        table.add(plantsButton).pad(10);
        table.add(animalsButton).pad(10);
        table.add(othersButton).pad(10);
        table.row();

        stage.addActor(table);
    }

    private void ShowOthersPage() {
    }

    private void ShowAnimalsPage() {
    }

    private void ShowPlantsPage() {
    }


    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    public void dispose() {
        stage.dispose();
        skin.dispose();
    }



    /*

    public ShowPlantsPage()
    {

    }

    public ShowAnimalsPage()
    {


    }

    public ShowOthersPage()
    {


    }

    public BuyJeep()
    {

    }

    public BuyPlant(Plant wanttobuy)
    {


    }

    public BuySecurity()
    {


    }

    public BuyRoad()
    {


    }

    public Showshop()
    {

    }

    */


}
