package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalImpl;
import io.github.safari.lwjgl3.positionable.npc.animals.EdibleCollection;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalSpecies;
import io.github.safari.lwjgl3.positionable.npc.animals.shared.AnimalType;
import io.github.safari.lwjgl3.positionable.npc.human.Poacher;
import io.github.safari.lwjgl3.positionable.npc.human.Ranger;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.positionable.visitors.Tourist;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;

import java.util.*;

import static io.github.safari.lwjgl3.maingame.GameController.getNextRoadTowardsEntrance;


public class GameModel implements EdibleCollection {
    private int money;
    private int income;
    private int monthlyexpense;
    private int speed; // 1 - 3
    private int difficulty; // 1 - 3
    private int dayspassed;
    private int consecutivesucmonthss = 0;
    private boolean isGameWon = false;

    private int ticketprice;
    private int touristcount;
    private Position entrancepos;

    private ArrayList<Herd> herds;
    private ArrayList<Poacher> poachers;
    private ArrayList<Ranger> rangers;
    private ArrayList<Jeep> jeeps;
    private ArrayList<Environment> environments;
    private ArrayList<Road> roads;
    private ArrayList<Tourist> tourists;

    ArrayList<Herd> allHerbivores = new ArrayList<>();
    ArrayList<HerbivoreEdible> allHerbivoreEdible = new ArrayList<>();
    ArrayList<Drinkable> allDrinkable = new ArrayList<>();


    @Override
    public List<HerbivoreEdible> getAllHerbivoreEdible() {
        return allHerbivoreEdible;
    }

    @Override
    public ArrayList<Herd> getAllHerbivores() {
        return allHerbivores;
    }

    @Override
    public ArrayList<Drinkable> getAllDrinkable() {
        return allDrinkable;
    }

    private int objectNumber = 80;
    private float mapWidth = 3200;
    private float mapHeight = 3200;
    private Random random;
    private boolean isDaytime = true;
    private float minDistance = 64;

    private float timeacc = 0;

    public GameModel(int difficulty)
    {
        this.difficulty = difficulty;
        this.random = new Random();
        this.herds = new ArrayList<>();
        this.rangers = new ArrayList<>();
        this.poachers = new ArrayList<>();
        this.dayspassed = 0;
        this.income = 0;
        this.touristcount = 0;
        this.speed = 1;

        environments = new ArrayList<>();
        this.jeeps = new ArrayList<>();
        this.roads = new ArrayList<>();
        this.tourists = new ArrayList<>();
        this.money = 500000;

        InitializeGame();
        GamemodelInstance.gameModel = this;
    }

    //Setters and getters


    public boolean isGameWon() {return isGameWon;}

    public void setSpeed(int speed) {
        this.speed = speed;
        clearAllActions();
    }

    private void clearAllActions() {
        for (Herd herd : herds) {
            for (AnimalImpl animal : herd.getAnimals()) {
                animal.clearActions();
            }
        }
    }

    public int getSpeed() {
        return speed;
    }

    public int getTimeMultiplicator() {
        return switch (speed) {
            case 1 -> 1;
            case 2 -> 7;
            case 3 -> 30;
            default -> throw new UnsupportedOperationException();
        };
    }


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getIncome() {
        return income;
    }

    public int getMoney() {
        return money;
    }

    public int getDayspassed() {
        return dayspassed;
    }

    public int getTouristcount() {return touristcount;}

    public void setTouristcount(int touristcount) {this.touristcount = touristcount;}
    public float getMapWidth(){
        return mapWidth;
    }

    public float getMapHeight(){
        return mapWidth;
    }

    public ArrayList<Environment> getEnvironments() {
        return environments;
    }

    public ArrayList<Herd> getHerds() {return herds;}

    public ArrayList<Jeep> getJeeps() {return jeeps;}

    public ArrayList<Road> getRoads(){ return roads;}

        public ArrayList<Tourist> getTourists() {return tourists;}
    public int getDifficulty() {return this.difficulty;}

    public void InitializeGame() {
        generateMap();
        initializePoachers();
    }

    private void generateMap() {
        int objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            int width = 96;
            int height = 110;

            if (positionFound(x, y, width, height)) {
                Tree tree = new Tree(new Position(x, y, width, height));
                environments.add(tree);
                allHerbivoreEdible.add(tree);
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            int width = 64;
            int height = 64;

            if (positionFound(x, y, width, height)) {
                Bush bush = new Bush(new Position(x, y, width, height));
                environments.add(bush);
                allHerbivoreEdible.add(bush);
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            int width = 96;
            int height = 96;

            if (positionFound(x, y, width, height)) {
                Lake lake = new Lake(new Position(x, y, width, height));
                environments.add(lake);
                allDrinkable.add(lake);
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            int width = 64;
            int height = 64;


            if (positionFound(x, y, width, height)) {
                Grass grass = new Grass(new Position(x, y, width, height));
                environments.add(grass);
                allHerbivoreEdible.add(grass);
                objectCount++;
            }
        }


        //Belepo kilepo
        boolean entranceb = false;
        boolean exitb = false;

        while (!entranceb || !exitb) {

            float gridSize = 64f;

            float entranceY = random.nextInt((int) (mapHeight / gridSize)) * gridSize;
            float exitY = random.nextInt((int) (mapHeight / gridSize)) * gridSize;


            if (positionFound(0, entranceY, 64, 64)) {
                Position entrapos = new Position(0, entranceY, 64, 64);
                Road entrance = new Road(entrapos, 2);
                this.entrancepos = entrapos;

                roads.add(entrance);
                //System.out.println("entrance:" + entrance.getRoadtype() + "   " + entrance.getPosition().getX() + "   " + entrance.getPosition().getY());
                entranceb = true;
            }

            if (positionFound(mapWidth - 64, exitY, 64, 64)) {
                Road exit = new Road(new Position(mapWidth - 64, exitY, 64, 64), 3);
                roads.add(exit);
                exitb = true;
            }
        }

        ArrayList<Position> obstacles = new ArrayList<>();
        for (Environment e : environments) {
            obstacles.add(e.getPosition());
        }
        PathGraph.generateStaticNodes(obstacles);


    }

    public boolean positionFound(float x, float y, int width, int height) {
        if (x < 0 || y < 0 || x + width > mapWidth || y + height > mapHeight) {
            return false;
        }

        for (Environment environment : environments) {
            if (environment.getPosition() == null) continue;

            float envX = environment.getPosition().getX();
            float envY = environment.getPosition().getY();
            int envWidth = environment.getPosition().getWidth();
            int envHeight = environment.getPosition().getHeight();

            if (x + width > envX && x < envX + envWidth && y + height > envY && y < envY + envHeight) {
                return false;
            }
        }


        for (Road road : roads) {
            Position pos = road.getPosition();

            if (Math.abs(pos.getX() - x) < 32 && Math.abs(pos.getY() - y) < 32) {
                System.out.println("ROAD BLOCKED");
                return false;
            }
        }


        return true;
    }

    public void Simulation(float delta) {
        int timeinterval = 1;

        if (speed == 2) {
            timeinterval = 7;
        } else if (speed == 3) {
            timeinterval = 30;
        }

        if (!isGameOver()) {
            int previousDays = dayspassed;
            timeacc += delta;
            SummonTourist();


            if (timeacc >= 10.0f) {
                dayspassed += timeinterval;
                timeacc = 0;
                isDaytime = !isDaytime;

                int previousMonth = previousDays / 30;
                int currentMonth = dayspassed / 30;
                spawnRandomPoachers();

                if (currentMonth > previousMonth) {
                    calculateIncome();
                    if(checkwincon()) isGameWon = true;
                }
            }

            for (Jeep jeep : jeeps) {
                Road roadtogo = getNextRoadTowardsEntrance(jeep, jeep.isTostart(), this);
                if (roadtogo != null) {
                    jeep.moveTowards(roadtogo.getPosition(), timeinterval);
                 }

                }
            }

    }

    public boolean checkwincon() {
        boolean allAboveThresholds =
            getTouristcount() >= 80 &&
                sumHerbivores() >= 50 &&
                sumPredators() >= 30 &&
                getMoney() >= 5000;

        if (allAboveThresholds) {
            consecutivesucmonthss++;
        } else {
            consecutivesucmonthss = 0;
        }

        return consecutivesucmonthss >= getRequiredMonths();

    }

    //HELPER OF CHECKWINCON
    int getRequiredMonths() {
        return switch (difficulty) {
            case 2 -> 6;
            case 3 -> 12;
            default -> 3;
        };
    }

    public void spawnRandomPoachers() {
        Random random = new Random();
        if (random.nextFloat() <= 0.3f) {
            int poacherCount = random.nextInt(3) + 1;
            for (int i = 0; i < poacherCount; i++) {
                float x = random.nextFloat() * getMapWidth();
                float y = random.nextFloat() * getMapHeight();

                Position position = new Position(x, y, 64, 64);
                Poacher poacher = new Poacher(position);

                poachers.add(poacher);

            }

            System.out.println(poacherCount + " new poacher(s) have appeared in the park!");
        }
    }

    public void initializePoachers() {
        poachers = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            float x = random.nextFloat() * getMapWidth();
            float y = random.nextFloat() * getMapHeight();

            Position position = new Position(x, y,64,64);
            Poacher poacher = new Poacher(position);

            poachers.add(poacher);
        }
    }

    public ArrayList<Poacher> getPoachers() {
        return poachers;
    }

    public ArrayList<Ranger> getRangers() {
        return rangers;
    }

    public boolean isDaytime() {
        return isDaytime;
    }

    public void increasemoney(int money) {
        this.money += money;
    }

    public void calculateIncome() {
        this.money += touristcount * 5 + (sumUniqueAnimals() * sumAnimals() * 3) - payrangers();
        this.income = touristcount * 5 + (sumUniqueAnimals() * sumAnimals() * 3) - payrangers();

    }

    void SummonTourist() {
        if (entrancepos == null) return;

        Random random = new Random();

        int offsetX = random.nextInt(65) - 32;
        int offsetY = random.nextInt(65) - 32;

        Position newPos = new Position(
            entrancepos.getX() + offsetX,
            entrancepos.getY() + offsetY,
            64, 64
        );


        if (touristcount < sumUniqueAnimals()) {
            this.touristcount = touristcount + 1;
            this.tourists.add(new Tourist(newPos));
            this.money += ticketprice;
        }
    }

    private int payrangers() {
        return rangers.size() * 50;

    }
    public boolean isGameOver() {return money <= 0 || sumAnimals() <= 0 && dayspassed > 15;}

    public void ChangeTicketPrice(int ticketprice) {
        this.ticketprice = ticketprice;
    }

    public int sumAnimals() {
        int sum = 0;
        for (Herd herd : herds) {
            sum += herd.animalCount();
        }

        return sum;
    }

    int sumUniqueAnimals() {
        Set<AnimalSpecies> uniqueAnimals = new HashSet<>();
        for (Herd herd : herds) {
            uniqueAnimals.add(herd.getAnimalSpecies());
        }

        return uniqueAnimals.size();
    }

    public int sumHerbivores() {
        int sum = 0;
        for (Herd herd : herds) {
            if (herd.getAnimalType().equals(AnimalType.HERBIVORE)) {
                sum += herd.animalCount();
            }
        }
        return sum;
    }

    public int sumPredators() {
        int sum = 0;
        for (Herd herd : herds) {
            if (herd.getAnimalType().equals(AnimalType.PREDATOR)) {
                sum += herd.animalCount();
            }
        }
        return sum;
    }


    public boolean CanBuy(ShopItem selectedItem) {
        return money - selectedItem.getPrice() >= 0;
    }

    public void addtoenvironment(Environment environment) {
        this.environments.add(environment);
    }

    public void addtojeeps(Jeep jeep) {
        this.jeeps.add(jeep);
    }

    public void addtoroads(Road road) {
        this.roads.add(road);
    }

    public void Decrease_My_Money(int decrease_amount) {
        this.money -= decrease_amount;
    }


    public boolean Is_There_Road(float x, float y) {
        for (Road road : roads) {

            if (Math.abs(road.getPosition().getX() - x) < 32)
                if (Math.abs(road.getPosition().getY() - y) < 32) {
                    return true;
                }

        }

        return false;

    }



    public Road getEntranceRoad() {
        for (Road road : roads) {
            if (road.getRoadtype() == 2) {
                return road;
            }
        }
        return null;
    }

    public Road getExitRoad() {
        for (Road road : roads) {
            if (road.getRoadtype() == 3) {
                return road;
            }
        }
        System.out.println("RETURNING NULL!");
        return null;
    }
}
