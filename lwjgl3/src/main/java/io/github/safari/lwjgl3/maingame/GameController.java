package io.github.safari.lwjgl3.maingame;

public class GameController {
    Shop shop;
    GameModel gameModel;


    public GameController(Shop shop, GameModel model)
    {
        this.shop = shop;
        this.gameModel = model;


    }

    public boolean TryToPlace(float x, float y,int width, int height, int pointer, int button)
    {
        ShopItem selectedItem = shop.getShopItems();

        if(selectedItem != null)
        {
            if(canplace(x,y,width, height))
            {
                if(gameModel.CanBuy(selectedItem))
                {
                    gameModel.BuyItem(selectedItem,x,y,width, height);
                    shop.clearSelection();
                    return true;
                }else
                {
                    System.out.println("InSufficient FundsException!");
                }
            }else
            {
                System.out.println("Target place ObstrcutedException!");
            }



        }else
        {
            System.out.println("Nincs Megveve semmi");
        }

    return false;

    }

    private boolean canplace(float x, float y,int width, int height)
    {
        return true;
    }



}
