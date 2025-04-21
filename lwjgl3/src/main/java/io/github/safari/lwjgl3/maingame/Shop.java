package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.safari.lwjgl3.util.ShopType;

import java.util.ArrayList;

public class Shop {
    private Window shopWindow;
    private boolean isVisible;
    private ShopType pageShown;
    private Table contentTable;
    private Skin skin;
    private GameModel MainGame;
    private TextButton[] PShownbuttons; //Ez is lehetne Arraylistes megoldas
    private ArrayList<TextButton> allItemButtons;
    private ShopItem SelectedItem;
    private boolean IsBuying;



    public Shop(Skin skin, Stage stage, GameModel MainGame) {
        this.skin = skin;
        this.pageShown = ShopType.PLANTS;
        this.MainGame = MainGame;
        this.isVisible = false;
        this.IsBuying = true;


        shopWindow = new Window("", skin);
        shopWindow.setSize(600, 600);
        shopWindow.setPosition(0,(Gdx.graphics.getHeight() / 2f - 150)); //dx.graphics.getHeight() / 2f - 200
        shopWindow.setMovable(false);
        shopWindow.setResizable(false);




        TextButton plantsButton = new TextButton("Plants", skin);
        TextButton animalsButton = new TextButton("Animals", skin);
        TextButton othersButton = new TextButton("Others", skin);



        this.PShownbuttons = new TextButton[3];
        this.allItemButtons = new ArrayList<>();

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


        Table buttonTable = new Table();
        buttonTable.add(plantsButton).width(100).height(50).pad(5);
        buttonTable.add(animalsButton).width(100).height(50).pad(5);
        buttonTable.add(othersButton).width(100).height(50).pad(5);
        shopWindow.add(buttonTable).row();


        contentTable = new Table();
        shopWindow.add(contentTable).row();


        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });


        Make_It_MoveAble();


        TextButton changePriceButton = createChangePriceButton();
        shopWindow.add(changePriceButton).pad(10).row();
        shopWindow.add(closeButton).pad(10).row();


        showPlantsPage();


        stage.addActor(shopWindow);
        shopWindow.setVisible(false);
    }

    public ShopItem getShopItems() {
        return  this.SelectedItem;
    }

    public boolean isBuying() {return IsBuying;}

    public void clearSelection()
    {
        this.SelectedItem = null;
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

    private void showPlantsPage() {
        pageShown = ShopType.PLANTS;
        resetPageButtonColors();
        PShownbuttons[0].getLabel().setColor(Color.YELLOW);
        updateContent(new ShopItem[]{
            new ShopItem("Tree", 50),
            new ShopItem("Lake", 20),
            new ShopItem("Bush", 30),
            new ShopItem("Grass", 10)
        });
    }

    private void showAnimalsPage() {
        pageShown = ShopType.ANIMALS;
        resetPageButtonColors();
        PShownbuttons[1].getLabel().setColor(Color.YELLOW); // Animals gomb kiemelése
        updateContent(new ShopItem[]{
            new ShopItem("Capybara", 100),
            new ShopItem("Mammoth", 500),
            new ShopItem("Dinosaur", 1000),
            new ShopItem("Lion", 2000)
        });
    }

    private void showOthersPage() {
        pageShown = ShopType.OTHERS;
        resetPageButtonColors();
        PShownbuttons[2].getLabel().setColor(Color.YELLOW); // Others gomb kiemelése
        updateContent(new ShopItem[]{
            new ShopItem("Road", 200),
            new ShopItem("Ranger", 500),
            new ShopItem("Camera", 300),
            new ShopItem("Jeep", 400)
        });
    }


    private void updateContent(ShopItem[] items) {
        contentTable.clear();
        allItemButtons.clear();

        for (ShopItem item : items) {
            TextButton itemButton = new TextButton(item.getName() + " - " + item.getPrice() + "$", skin);
            allItemButtons.add(itemButton);
            SetFontsize(0.8f, itemButton);
            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    resetItemButtonColors();
                    itemButton.getLabel().setColor(Color.YELLOW);
                    SelectedItem = item;
                    IsBuying = true;
                    System.out.println(item.getName() + " selected! Price: " + item.getPrice());

                }
            });
            TextButton sellbutton = new TextButton("Sell" + " - " + item.getPrice() * 0.7 + "$", skin);
            allItemButtons.add(sellbutton);
            SetFontsize(0.6f, sellbutton);
            sellbutton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    resetItemButtonColors();
                    sellbutton.getLabel().setColor(Color.YELLOW);
                    SelectedItem = item;
                    IsBuying = false;
                    System.out.println(item.getName() + " selected for sell! Price: " + item.getPrice() * 0.7);

                }
            });



            contentTable.add(itemButton).pad(5);
            contentTable.add(sellbutton).width(150).height(75).pad(5);
            contentTable.row();
        }
    }

    private TextButton createChangePriceButton() {
        TextButton changePriceButton = new TextButton("Change Ticket Price", skin);
        changePriceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Set New Ticket Price", skin) {
                    @Override
                    protected void result(Object object) {
                        if (object instanceof Boolean && (Boolean) object) {
                            TextField inputField = findActor("priceInput");
                            try {
                                float newPrice = Float.parseFloat(inputField.getText());
                                if (newPrice > 0) {
                                    MainGame.ChangeTicketPrice((int)newPrice);
                                    System.out.println("Ticket price changed to: " + newPrice);
                                } else {
                                    System.out.println("Price must be positive.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number format.");
                            }
                        }
                    }
                };

                TextField inputField = new TextField("", skin);
                inputField.setMessageText("Enter new price");
                inputField.setName("priceInput");

                dialog.getContentTable().add(inputField).width(200).pad(10);
                dialog.button("OK", true);
                dialog.button("Cancel", false);
                dialog.show(shopWindow.getStage());
            }
        });

        return changePriceButton;
    }


    private void SetFontsize(float size, TextButton... buttons)
    {
        for (TextButton button : buttons)
        {
            button.getLabel().setFontScale(size);


        }
    }


    private void resetPageButtonColors() {
        for (TextButton button : PShownbuttons) {
            button.getLabel().setColor(Color.WHITE);
        }
    }

    private void resetItemButtonColors() {
        for (TextButton button : allItemButtons) {
            button.getLabel().setColor(Color.WHITE);
        }
    }

    private void Make_It_MoveAble()
    {
        shopWindow.addListener(new ClickListener() {
            private float dragOffsetX, dragOffsetY;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                dragOffsetX = x;
                dragOffsetY = y;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float newX = shopWindow.getX() + (x - dragOffsetX);
                float newY = shopWindow.getY() + (y - dragOffsetY);
                shopWindow.setPosition(newX, newY);
            }
        });
    }


}
