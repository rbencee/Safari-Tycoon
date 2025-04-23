package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;

public class GameController {
    Shop shop;
    GameModel gameModel;
    GameView gameView;


    public GameController(Shop shop, GameModel model, GameView gameView) {
        this.shop = shop;
        this.gameModel = model;
        this.gameView = gameView;


    }

    public boolean TryToPlace(float x, float y, int width, int height, int pointer, int button, boolean isjeep) {
        ShopItem selectedItem = shop.getShopItems();

        if (selectedItem != null) {
            if (shop.isBuying()) {
                if (gameModel.positionFound(x, y, width, height) || isjeep) {
                    if (gameModel.CanBuy(selectedItem)) {
                        if (!isjeep) {
                            BuyItem(selectedItem, x, y, width, height);
                            //shop.clearSelection();
                            return true;
                        } else {
                            if (gameModel.Is_There_Road(x, y)) {
                                BuyItem(selectedItem, x, y, width, height);

                            } else {
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
                SellThis(x, y, selectedItem);
            }


        } else {
            System.out.println("Nincs Megveve semmi");
        }

        return false;

    }

    private void BuyItem(ShopItem item, float x, float y, int width, int height) {
        System.out.println(item.getName());

        gameModel.Decrease_My_Money(item.getPrice());
        Animal animal = null;
        Herd herd = null;

        switch (item.getName()) {
            case "Capybara":
                animal = AnimalFactory.createCapybara(new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
                herd.addToHerd((AnimalImpl) animal);
                gameModel.getHerds().add(herd);
                gameModel.getAllHerbivores().add(herd);
                System.out.println("Capybara buy successful!");
                gameView.getGameStage().addActor(herd);
                break;
            case "Mammoth":
                animal = AnimalFactory.createMammoth(new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.MAMMOTH, Behaviour.createHerbivoreBehaviours());
                herd.addToHerd((AnimalImpl) animal);
                gameModel.getHerds().add(herd);
                gameModel.getAllHerbivores().add(herd);
                System.out.println("Mammoth buy successful!");
                gameView.getGameStage().addActor(herd);
                break;
            case "Dinosaur":
                animal = AnimalFactory.createDinosaur(new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.DINOSAUR, Behaviour.createPredatorBehaviours());
                herd.addToHerd((AnimalImpl) animal);
                gameModel.getHerds().add(herd);
                System.out.println("Dinosaur buy successful!");
                gameView.getGameStage().addActor(herd);
                break;
            case "Lion":
                animal = AnimalFactory.createLion(new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.LION, Behaviour.createPredatorBehaviours());
                herd.addToHerd((AnimalImpl) animal);
                gameModel.getHerds().add(herd);
                System.out.println("Lion buy successful!");
                gameView.getGameStage().addActor(herd);
                break;
            case "Bush":
                Bush bush = new Bush(new Position(x, y, width, height));
                gameModel.addtoenvironment(bush);
                PathGraph.addObstacle(bush.getPosition());
                gameModel.getAllHerbivoreEdible().add(bush);
                break;
            case "Tree":
                Tree tree = new Tree(new Position(x, y, width, height));
                gameModel.addtoenvironment(tree);
                PathGraph.addObstacle(tree.getPosition());
                gameModel.getAllHerbivoreEdible().add(tree);
                break;
            case "Lake":
                Lake lake = new Lake(new Position(x, y, width, height));
                gameModel.addtoenvironment(lake);
                PathGraph.addObstacle(lake.getPosition());
                gameModel.getAllDrinkable().add(lake);
                break;
            case "Grass":
                Grass grass = new Grass(new Position(x, y, width, height));
                gameModel.addtoenvironment(grass);
                PathGraph.addObstacle(grass.getPosition());
                gameModel.getAllHerbivoreEdible().add(grass);
                break;
            case "Road":
                Road road = new Road(new Position(x, y, width, height));
                gameModel.addtoroads(road);
                break;
            case "Jeep":
                Jeep jeep = new Jeep(new Position(x, y, width, height));
                gameModel.addtojeeps(jeep);
                break;
            default:
                System.out.println("Not Implemented yet!");
                break;
        }
    }


    private void SellThis(float x, float y, ShopItem item) {
        String item_to_sell = item.getName();
        float sellRadius = 100f;
        float minDistSq = Float.MAX_VALUE;
        Environment closestEnvironment = null;
        Herd closestHerd = null;
        Road closestRoad = null;
        Jeep closestJeep = null;

        for (Environment environment : gameModel.getEnvironments()) {
            boolean matches = switch (item_to_sell) {
                case "Grass" -> environment instanceof Grass;
                case "Tree" -> environment instanceof Tree;
                case "Bush" -> environment instanceof Bush;
                case "Lake" -> environment instanceof Lake;
                default -> false;
            };

            if (matches) {
                float dx = environment.getPosition().getX() - x;
                float dy = environment.getPosition().getY() - y;
                float distSq = dx * dx + dy * dy;

                if (distSq <= sellRadius * sellRadius && distSq < minDistSq) {
                    minDistSq = distSq;
                    closestEnvironment = environment;
                }
            }
        }
        for (Herd herd : gameModel.getHerds()) {
            boolean matches = switch (item_to_sell) {
                case "Capybara" -> herd.getAnimalSpecies() == AnimalSpecies.CAPYBARA;
                case "Dinosaur" -> herd.getAnimalSpecies() == AnimalSpecies.DINOSAUR;
                case "Mammoth" -> herd.getAnimalSpecies() == AnimalSpecies.MAMMOTH;
                case "Lion" -> herd.getAnimalSpecies() == AnimalSpecies.LION;
                default -> false;
            };

            if (matches && !herd.getAnimals().isEmpty()) {
                Animal animal = herd.getAnimals().get(0);
                float dx = animal.getPosition().getX() - x;
                float dy = animal.getPosition().getY() - y;
                float distSq = dx * dx + dy * dy;

                if (distSq <= sellRadius * sellRadius && distSq < minDistSq) {
                    minDistSq = distSq;
                    closestHerd = herd;
                }
            }
        }

        for (Road road : gameModel.getRoads()) {
            if (item_to_sell.equals("Road")) {
                float dx = road.getPosition().getX() - x;
                float dy = road.getPosition().getY() - y;
                float distSq = dx * dx + dy * dy;

                if (distSq <= sellRadius * sellRadius && distSq < minDistSq) {
                    minDistSq = distSq;
                    closestRoad = road;
                }
            }
        }

        for (Jeep jeep : gameModel.getJeeps()) {
            if (item_to_sell.equals("Jeep")) {
                float dx = jeep.getPosition().getX() - x;
                float dy = jeep.getPosition().getY() - y;
                float distSq = dx * dx + dy * dy;

                if (distSq <= sellRadius * sellRadius && distSq < minDistSq) {
                    minDistSq = distSq;
                    closestJeep = jeep;
                }
            }
        }

        // Eltávolítás prioritás: Environment > Herd > Road > Jeep
        if (closestEnvironment != null) {
            gameModel.getEnvironments().remove(closestEnvironment);
            gameModel.increasemoney((int) (item.getPrice() * 0.7f));
            return;
        }

        if (closestHerd != null) {
            gameModel.getHerds().remove(closestHerd);
            gameModel.increasemoney((int) (item.getPrice() * 0.7f));
            return;
        }

        if (closestRoad != null) {
            gameModel.getRoads().remove(closestRoad);
            gameModel.increasemoney((int) (item.getPrice() * 0.7f));
            return;
        }

        if (closestJeep != null) {
            gameModel.getJeeps().remove(closestJeep);
            gameModel.increasemoney((int) (item.getPrice() * 0.7f));
        }
    }


}





