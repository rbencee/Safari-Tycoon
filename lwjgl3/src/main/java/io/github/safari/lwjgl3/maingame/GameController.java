package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.behaviours.Behaviour;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.positionable.visitors.Tourist;
import io.github.safari.lwjgl3.util.exceptions.InSufficientFundsException;
import io.github.safari.lwjgl3.util.exceptions.ObstructedException;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;

import java.util.*;

public class GameController {
    Shop shop;
    GameModel gameModel;
    GameView gameView;
    private static Ranger selectedRanger;
    private static boolean waitingForTarget = false;

    public GameController(Shop shop, GameModel model, GameView gameView) {
        this.shop = shop;
        this.gameModel = model;
        this.gameView = gameView;


    }


    public static void setWaitingForTarget(boolean waiting) {
        waitingForTarget = waiting;
    }

    public static boolean isWaitingForTarget() {
        return waitingForTarget;
    }

    public static void selectRanger(Ranger ranger) {
        if (selectedRanger != null) {
            selectedRanger.setSelected(false);
        }

        selectedRanger = ranger;

        if (ranger != null) {
            ranger.setSelected(true);
            System.out.println("Ranger selected! Select a target (animal or poacher)");
        }
    }
    public static boolean isRangerSelected() {
        return selectedRanger != null;
    }

    public static void selectTargetAt(float x, float y, GameModel gameModel) {
        if (selectedRanger == null) return;

        float selectionRadius = 100f;
        float minDistSq = Float.MAX_VALUE;
        Animal closestAnimal = null;
        Poacher closestPoacher = null;


        for (Herd herd : gameModel.getHerds()) {
            for (Animal animal : herd.getAnimals()) {
                float dx = animal.getPosition().getX() - x;
                float dy = animal.getPosition().getY() - y;
                float distSq = dx * dx + dy * dy;

                if (distSq <= selectionRadius * selectionRadius && distSq < minDistSq) {
                    minDistSq = distSq;
                    closestAnimal = animal;
                }
            }
        }

        for (Poacher poacher : gameModel.getPoachers()) {
            float dx = poacher.getPosition().getX() - x;
            float dy = poacher.getPosition().getY() - y;
            float distSq = dx * dx + dy * dy;

            if (distSq <= selectionRadius * selectionRadius && distSq < minDistSq) {
                minDistSq = distSq;
                closestPoacher = poacher;
            }
        }

        if (closestAnimal != null) {
            selectedRanger.setTargetAnimal(closestAnimal);
            System.out.println("Animal target selected.");
        } else if (closestPoacher != null) {
            selectedRanger.setTargetPoacher(closestPoacher);
            System.out.println("Poacher target selected.");
        } else {
            System.out.println("No target found near click.");
        }

    }


    public boolean TryToPlace(float x, float y, int width, int height, int pointer, int button, boolean isjeep) {
        ShopItem selectedItem = shop.getShopItems();

        try {
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
                            throw new InSufficientFundsException();
                        }
                    } else {
                        throw new ObstructedException();
                    }
                } else {
                    SellThis(x, y, selectedItem);
                }


            } else {
                System.out.println("Nincs Megveve semmi");
            }
        }
        catch(Exception e)
        {
            e.getMessage();
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
                animal = AnimalFactory.createNew(AnimalSpecies.CAPYBARA, new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.CAPYBARA, Behaviour.createHerbivoreBehaviours());
                herd.addToHerd((AnimalImpl) animal);
                gameModel.getHerds().add(herd);
                gameModel.getAllHerbivores().add(herd);
                System.out.println("Capybara buy successful!");
                gameView.getGameStage().addActor(herd);
                break;
            case "Mammoth":
                animal = AnimalFactory.createNew(AnimalSpecies.MAMMOTH, new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.MAMMOTH, Behaviour.createHerbivoreBehaviours());
                herd.addToHerd((AnimalImpl) animal);
                gameModel.getHerds().add(herd);
                gameModel.getAllHerbivores().add(herd);
                System.out.println("Mammoth buy successful!");
                gameView.getGameStage().addActor(herd);
                break;
            case "Dinosaur":
                animal = AnimalFactory.createNew(AnimalSpecies.DINOSAUR, new Position(x, y, width, height));
                herd = new Herd(AnimalSpecies.DINOSAUR, Behaviour.createPredatorBehaviours());
                herd.addToHerd((AnimalImpl) animal);
                gameModel.getHerds().add(herd);
                System.out.println("Dinosaur buy successful!");
                gameView.getGameStage().addActor(herd);
                break;
            case "Lion":
                animal = AnimalFactory.createNew(AnimalSpecies.LION, new Position(x, y, width, height));
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
            case "Ranger":
                Ranger ranger = new Ranger(new Position(x, y, width, height));
                gameModel.getRangers().add(ranger);
                System.out.println("Ranger buy successful!");
                gameView.getGameStage().addActor(ranger);
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

    public static List<Road> getAdjacentRoads(Road road ,GameModel gameModel) {
        List<Road> adjacent = new ArrayList<>();
        Position pos = road.getPosition();

        for (Road other : gameModel.getRoads()) {
            if (other == road) continue;

            Position otherPos = other.getPosition();

            float dx = Math.abs(pos.getX() - otherPos.getX());
            float dy = Math.abs(pos.getY() - otherPos.getY());

            if ((dx == 64 && dy == 0) || (dx == 0 && dy == 64)) {
                adjacent.add(other);
            }
        }

        return adjacent;
    }


    public static Road getNextRoadTowardsEntrance(Jeep jeep, boolean isentrancedestionation, GameModel gameModel) {
        System.out.println("GOTO: " +  isentrancedestionation);
        Position startPos = jeep.getPosition();
        Road startRoad = getClosestRoad(startPos,gameModel);
        Road entrance;
        if(isentrancedestionation) {
            entrance = gameModel.getEntranceRoad();
        } else
        {
            entrance = gameModel.getExitRoad();
        }


        if (startRoad == null || entrance == null) return null;
        System.out.println("Not triggered");


        if (startRoad.getPosition().equals(entrance.getPosition())) {
            if(isentrancedestionation)
            {
                CheckForTourist_Here(jeep.getPosition(), jeep, gameModel);
            } else
            {
                gameModel.setTouristcount(gameModel.getTouristcount() - jeep.Drop_Off_Tourists());
            }

            jeep.setTostart(!jeep.isTostart());
        }


        Map<Road, Road> cameFrom = new HashMap<>();
        Queue<Road> queue = new LinkedList<>();
        Set<Road> visited = new HashSet<>();

        queue.add(startRoad);
        visited.add(startRoad);
        cameFrom.put(startRoad, null);

        while (!queue.isEmpty()) {
            Road current = queue.poll();

            if (current.equals(entrance)) {

                Road step = current;
                Road prev = cameFrom.get(step);

                while (prev != null && !prev.equals(startRoad)) {
                    step = prev;
                    prev = cameFrom.get(step);
                }

                return step;
            }

            for (Road neighbor : getAdjacentRoads(current, gameModel)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return null; // Nincs út
    }

    private static Road getClosestRoad(Position pos, GameModel gameModel) {
        Road closest = null;
        float minDist = Float.MAX_VALUE;

        for (Road road : gameModel.getRoads()) {
            float dx = pos.getX() - road.getPosition().getX();
            float dy = pos.getY() - road.getPosition().getY();
            float dist = dx * dx + dy * dy;

            if (dist < minDist) {
                minDist = dist;
                closest = road;
            }
        }

        return closest;
    }

    private static void CheckForTourist_Here(Position position, Jeep jeep, GameModel gameModel)
    {
        Iterator<Tourist> iterator = gameModel.getTourists().iterator();
        while (iterator.hasNext()) {
            Tourist t = iterator.next();
            if (jeep.trytoaddtourist(t)) {
                iterator.remove();
            }
        }
    }



}





