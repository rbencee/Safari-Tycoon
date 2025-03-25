package io.github.safari.lwjgl3.maingame;

public class GameController {
    Shop shop;
    GameModel gameModel;


    public GameController(Shop shop, GameModel model)
    {
        this.shop = shop;
        this.gameModel = model;





    }

    public boolean TryToPlace(int x, int y, int pointer, int button)
    {
        ShopItem selectedItem = shop.getShopItems();

        if(selectedItem == null)
        {
            if(canplace(x,y))
            {
                if(gameModel.CanBuy(selectedItem))
                {
                    gameModel.BuyItem(selectedItem,x,y);
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

    private boolean canplace(int x, int y)
    {
        return true;
    }


}
