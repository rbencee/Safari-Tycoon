package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.npc.human.*;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.security.Security;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;

import java.util.*;


public class GameModel implements EdibleCollection{
    private int money;
    private int income;
    private int monthlyexpense;
    private int speed; // 1 - 3
    private int difficulty; // 1 - 3
    private int dayspassed;

    private int ticketprice;
    private int touristcount;

    private ArrayList<Herd> herds;
    private ArrayList<Poacher> poachers;
    private ArrayList<Ranger> rangers;
    private ArrayList<Jeep> jeeps;
    private ArrayList<Security> securities;
    private ArrayList<Environment> environments;


    ArrayList<Animal> allHerbivores = new ArrayList<>();
    ArrayList<HerbivoreEdible> allHerbivoreEdible = new ArrayList<>();
    ArrayList<Drinkable> allDrinkable = new ArrayList<>();


    @Override
    public List<HerbivoreEdible> getAllHerbivoreEdible() {
        return allHerbivoreEdible;
    }

    @Override
    public ArrayList<Animal> getAllHerbivores() {
        return allHerbivores;
    }

    @Override
    public ArrayList<Drinkable> getAllDrinkable() {
        return allDrinkable;
    }

    private int objectNumber = 50;
    private float mapWidth = 3200;
    private float mapHeight = 3200;
    private Random random;
    private float minDistance = 64;

    private float timeacc = 0;

    public GameModel(int difficulty)
    {
        this.difficulty = difficulty;
        this.random = new Random();
        this.herds = new ArrayList<>();
        this.rangers = new ArrayList<>();
        this.dayspassed = 0;
        this.income = 0;
        this.touristcount = 0;
        this.speed = 1;

        environments = new ArrayList<>();
        this.money = 5000;

        InitializeGame();
        AnimalFactory.gameModel = this;
    }

    //Setters and getters


    public void setSpeed(int speed) {
        this.speed = speed;
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

    public int getTouristcount() {
        return touristcount;
    }

    public ArrayList<Environment> getEnvironments() {return environments;}

    public ArrayList<Herd> getHerds() {return herds;}

    public void InitializeGame()
    {
        generateMap();
    }

    private void generateMap()
    {
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
        ArrayList<Position> obstacles = new ArrayList<>();
        for(Environment e : environments){
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
        return true;
    }

    public void Simulation(float delta)
    {
        int timeinterval = 1;

        if(speed == 2)
        {
            timeinterval = 7;
        }
        else if(speed == 3)
        {
            timeinterval = 30;
        }

        if(!isGameOver())
        {
            int previousDays = dayspassed;
            timeacc += delta;

            if(timeacc >= 3.0f)
            {
                dayspassed += timeinterval;
                timeacc = 0;

                if((dayspassed - previousDays) % 30 == 0) {
                    calculateIncome();
                }
            }
        }

    }

    public void increasemoney(int money)
    {
        this.money += money;
    }

    public void calculateIncome()
    {
        this.money += touristcount * 5 + (sumUniqueAnimals() * sumAnimals() * 3) - payrangers();
        this.income = touristcount * 5 + (sumUniqueAnimals() * sumAnimals() * 3) - payrangers();

    }

    private int CalculateTourist() //Turistakat ad hozza ha kell
    {
        return sumUniqueAnimals();
    }

    private int payrangers()
    {
        return rangers.size() * 50;

    }

    private boolean isGameOver()
    {
        return false;
    }

    public void ChangeTicketPrice(int ticketprice)
    {
        this.ticketprice = ticketprice;
    }

    private int sumAnimals()//Kell herdbe egy cucc, ammi visszaadja hogy hany allat van benne
    {
        int sum = 0;
        for (Herd herd : herds)
        {
            sum += herd.animalcount(); //Lehet meg kell nezni hogy biztos el e.
        }

        return sum;
    }

    private int sumUniqueAnimals()
    {
        Set<AnimalSpecies> uniqueAnimals = new HashSet<>();
        for (Herd herd : herds) {
            uniqueAnimals.add(herd.getAnimalSpecies());
        }

        return uniqueAnimals.size();
    }

    public int sumHerbivores(){
        int sum = 0;
        for (Herd herd : herds){
            if (herd.getAnimalSpecies().getAnimalType().equals(AnimalType.HERBIVORE)){
                sum += herd.animalcount();
            }
        }
        return sum;
    }

    public int sumPredators(){
        int sum = 0;
        for (Herd herd : herds){
            if (herd.getAnimalSpecies().getAnimalType().equals(AnimalType.PREDATOR)){
                sum += herd.animalcount();
            }
        }
        return sum;
    }





    public boolean CanBuy(ShopItem selectedItem)
    {
        if(money - selectedItem.getPrice() >= 0)
        {
            return true;
        }
        return false;
    }






    /*
    private CheckInRange()
    {

    }
    */
}
