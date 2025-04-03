package io.github.safari.lwjgl3.maingame;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.objects.Bush;
import io.github.safari.lwjgl3.positionable.objects.Grass;
import io.github.safari.lwjgl3.positionable.objects.Lake;
import io.github.safari.lwjgl3.positionable.objects.Tree;

public class GameController {
    Shop shop;
    GameModel gameModel;
    GameView gameView;


    public GameController(Shop shop, GameModel model, GameView gameView)
    {
        this.shop = shop;
        this.gameModel = model;
        this.gameView = gameView;


    }

    public boolean TryToPlace(float x, float y,int width, int height, int pointer, int button)
    {
        ShopItem selectedItem = shop.getShopItems();

        if(selectedItem != null)
        {
            if(gameModel.positionFound(x,y,width, height))
            {
                if(gameModel.CanBuy(selectedItem))
                {
                    buyItem(selectedItem,x,y,width, height);
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
    public void buyItem(ShopItem item, float x,float y,int width, int height) {
        System.out.println(item.getName());

        Animal animal = null;
        Herd herd = null;

        switch (item.getName()) {
            case "Capybara":
                animal = AnimalFactory.createCapybara(new Position(x, y, width, height));  // Új Capybara példány létrehozása a megadott koordinátákkal
                herd = new Herd(AnimalSpecies.CAPYBARA);
                herd.addToHerd(animal);
                gameModel.getHerds().add(herd);
                System.out.println("Capybara buy successful!");
                animal.addAction(Actions.moveBy(100,100, 5));
                gameView.getGameStage().addActor((AnimalImpl) animal);
                System.out.println("Animal added to gameStage: " + (gameView.getGameStage().getActors().contains((Actor)animal, true)));

                break;
            case "Mammoth":
                break;
            case "Dinosaur":
                animal = AnimalFactory.createDinosaur(new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.DINOSAUR);
                herd.addToHerd(animal);
                System.out.println("Dinosaur buy successful!");
                break;
            case "Lion":
                break;
            case "Bush":
                Bush bush = new Bush(new Position(x, y, width, height));
                break;
            case "Tree":
                Tree tree = new Tree(new Position(x, y, width, height));
                break;
            case "Lake":
                Lake lake = new Lake(new Position(x, y, width, height));
                break;
            case "Grass":
                Grass grass = new Grass(new Position(x, y, width, height));
                break;
            default:
                System.out.println("Not Implemented yet!");
                break;
        }
    }




}
