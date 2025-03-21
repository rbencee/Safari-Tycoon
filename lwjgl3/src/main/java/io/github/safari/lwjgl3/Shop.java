package io.github.safari.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.safari.lwjgl3.maingame.GameModel;
import io.github.safari.lwjgl3.util.ShopType;

import java.util.ArrayList;

public class Shop {
    private Window shopWindow;
    private boolean isVisible;
    private ShopType pageShown;
    private Table contentTable; // Dinamikus tartalomhoz
    private Skin skin;
    private GameModel MainGame;
    private TextButton[] PShownbuttons;
    //Kontrollernek kellene lehet inkabb kuldeni a dolgokat


    public Shop(Skin skin, Stage stage, GameModel MainGame) {
        this.skin = skin;
        this.pageShown = ShopType.PLANTS;
        this.MainGame = MainGame;
        this.isVisible = false;


        shopWindow = new Window("", skin);
        shopWindow.setSize(600, 600);
        shopWindow.setPosition(0,(Gdx.graphics.getHeight() / 2f - 150)); //dx.graphics.getHeight() / 2f - 200
        shopWindow.setMovable(false);
        shopWindow.setResizable(false);

        // Kategória gombok létrehozása
        TextButton plantsButton = new TextButton("Plants", skin);
        TextButton animalsButton = new TextButton("Animals", skin);
        TextButton othersButton = new TextButton("Others", skin);



        this.PShownbuttons = new TextButton[3];

        PShownbuttons[0] = plantsButton;
        PShownbuttons[1] = animalsButton;
        PShownbuttons[2] = othersButton;

        SetFontsize(0.5f, PShownbuttons);



        plantsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPlantsPage();
            }
        });

        animalsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showAnimalsPage();
            }
        });

        othersButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showOthersPage();
            }
        });

        // Gombok hozzáadása az ablakhoz
        Table buttonTable = new Table();
        buttonTable.add(plantsButton).width(100).height(50).pad(5);
        buttonTable.add(animalsButton).width(100).height(50).pad(5);
        buttonTable.add(othersButton).width(100).height(50).pad(5);
        shopWindow.add(buttonTable).row();

        // Tartalom táblázat
        contentTable = new Table();
        shopWindow.add(contentTable).row();

        // Bezárás gomb
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        shopWindow.add(closeButton).pad(10).row();

        // Alapból növények oldalt mutatjuk
        showPlantsPage();

        // Hozzáadás a Stage-hez
        stage.addActor(shopWindow);
        shopWindow.setVisible(false);
    }



    public void show() {
        isVisible = true;
        shopWindow.setVisible(true);
    }

    public void hide() {
        isVisible = false;
        shopWindow.setVisible(false);
    }

    public boolean isVisible() {
        return isVisible;
    }

    // Növények oldala
    private void showPlantsPage() {
        pageShown = ShopType.PLANTS;
        updateContent(new String[]{"Tree - 50$", "Lake - 20$", "Bush - 30$", "Grass - 10$"});
    }

    // Állatok oldala
    private void showAnimalsPage() {
        pageShown = ShopType.ANIMALS;
        updateContent(new String[]{"Capybara - 100$", "Mammoth - 500$", "Dinosaur - 1000$", "Lion-2000$"});
    }

    // Egyéb tárgyak oldala
    private void showOthersPage() {
        pageShown = ShopType.OTHERS;
        updateContent(new String[]{"Road - 200$", "Ranger - 500$", "Camera - 300$", "Jeep - 400$"});
    }

    // Tartalom frissítése a kiválasztott kategóriához
    private void updateContent(String[] items) {
        contentTable.clear();

        for (String item : items) {
            TextButton itemButton = new TextButton(item, skin);
            SetFontsize(0.8f,itemButton);
            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(item + " purchased!");
                    /*
                    if(MainGame.CanBuy())
                    {
                        MainGame.BuySelected(String event);
                    }

                     */
                }
            });
            contentTable.add(itemButton).pad(5).row();
        }
    }

    private void SetUpButtons()
    {

    }

    private void SetFontsize(float size, TextButton... buttons)
    {
        for (TextButton button : buttons)
        {
            button.getLabel().setFontScale(size);


        }
    }
}
