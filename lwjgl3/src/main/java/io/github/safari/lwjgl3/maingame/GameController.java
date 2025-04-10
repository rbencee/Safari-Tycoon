package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;

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

    public boolean TryToPlace(float x, float y, int width, int height, int pointer, int button, boolean isjeep) {
        ShopItem selectedItem = shop.getShopItems();

        if (selectedItem != null) {
            if (gameModel.positionFound(x, y, width, height) || isjeep){
                if (gameModel.CanBuy(selectedItem)) {
                    if(!isjeep) {
                        BuyItem(selectedItem, x, y, width, height);
                        //shop.clearSelection();
                        return true;
                    } else
                    {
                        if(gameModel.Is_There_Road(x, y))
                        {
                            BuyItem(selectedItem,x,y,width,height);

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



        }else
        {
            System.out.println("Nincs Megveve semmi");
        }

        return false;

    }
    private void BuyItem(ShopItem item, float x,float y,int width, int height) {
        System.out.println(item.getName());

        gameModel.Decrease_My_Money(item.getPrice());
        Animal animal = null;
        Herd herd = null;

        switch (item.getName()) {
            case "Capybara":
                animal = AnimalFactory.createCapybara(new Position(x, y, width, height));  // Új Capybara példány létrehozása a megadott koordinátákkal
                herd = new Herd(AnimalSpecies.CAPYBARA);
                herd.addToHerd(animal);
                gameModel.getHerds().add(herd);
                System.out.println("Capybara buy successful!");
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
                gameModel.addtoenvironment(bush);
                break;
            case "Tree":
                Tree tree = new Tree(new Position(x, y, width, height));
                gameModel.addtoenvironment(tree);
                break;
            case "Lake":
                Lake lake = new Lake(new Position(x, y, width, height));
                gameModel.addtoenvironment(lake);
                break;
            case "Grass":
                Grass grass = new Grass(new Position(x, y, width, height));
                gameModel.addtoenvironment(grass);
                break;
            case "Road":
                Road road= new Road(new Position(x,y, width, height));
                gameModel.addtoroads(road);
                break;
            case "Jeep":
                Jeep jeep= new Jeep(new Position(x,y, width, height));
                gameModel.addtojeeps(jeep);
                break;
            default:
                System.out.println("Not Implemented yet!");
                break;
        }
    }

}





