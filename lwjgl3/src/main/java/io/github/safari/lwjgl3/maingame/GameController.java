package io.github.safari.lwjgl3.maingame;


public class GameController {
    Shop shop;
    GameModel gameModel;


    public GameController(Shop shop, GameModel model) {
        this.shop = shop;
        this.gameModel = model;


    }

    public boolean TryToPlace(float x, float y, int width, int height, int pointer, int button, boolean isjeep) {
        ShopItem selectedItem = shop.getShopItems();

        if (selectedItem != null) {
            if (gameModel.positionFound(x, y, width, height)) {
                if (gameModel.CanBuy(selectedItem)) {
                    if(!isjeep) {
                        gameModel.BuyItem(selectedItem, x, y, width, height);
                        //shop.clearSelection();
                        return true;
                    } else
                    {
                        if(gameModel.Is_There_Road(x, y))
                        {
                            gameModel.BuyItem(selectedItem,x,y,width,height);

                        } else
                        {
                            System.out.println("No suitable Road Found");
                        }
                    }
                } else {
                    System.out.println("InSufficient FundsException!");
                }
            } else {
                System.out.println("Target place ObstrcutedException!");
            }


        } else {
            System.out.println("Nincs Megveve semmi");
        }

        return false;

    }


}





