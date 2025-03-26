package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.positionable.npc.animals.AnimalSpecies;
import io.github.safari.lwjgl3.positionable.npc.human.*;
import io.github.safari.lwjgl3.positionable.objects.*;




import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.security.Security;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.positionable.visitors.Tourist;

import java.util.ArrayList;
import java.util.Random;


public class GameModel {
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

    private int objectNumber = 50;
    private float mapWidth = 3200;
    private float mapHeight = 3200;
    private Random random;
    private float minDistance = 64;



    public GameModel(int difficulty)
    {
        this.difficulty = difficulty;
        this.random = new Random();
        this.herds = new ArrayList<>();

        environments = new ArrayList<>();
        this.money = 5000;

        InitializeGame();
    }

    //Setters and getters

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
            int height = 124;

            if (positionFound(x, y, minDistance)) {
                environments.add(new Tree(new Position(x, y, width, height)));
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            int width = 42;
            int height = 42;

            if (positionFound(x, y, minDistance)) {
                environments.add(new Bush(new Position(x, y, width, height)));
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            int width = 92;
            int height = 92;

            if (positionFound(x, y, minDistance)) {
                environments.add(new Lake(new Position(x, y, width, height)));
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            int width = 64;
            int height = 58;


            if (positionFound(x, y, minDistance)) {
                environments.add(new Grass(new Position(x, y, width, height)));
                objectCount++;
            }
        }

    }

    private boolean positionFound(float x, float y,float minDistance){
        for(Environment environment : environments){
            if (environment.getPosition() == null) continue;
            float distance = (float) Math.sqrt(Math.pow(x - environment.getPosition().getX(), 2) + Math.pow(y - environment.getPosition().getY(), 2));
            if(distance < minDistance){
                return false;
            }
        }
        return true;
    }

    public void Simulation()
    {
        while(!isGameOver())
        {


            calculateIncome();

        }



    }

    public void increasemoney(int money)
    {
        this.money += money;
    }

    public void calculateIncome()
    {

        this.money += touristcount * 5 + (sumuniqueanimals() * sumanimals() * 3) - payrangers();

    }

    private int CalculateTourist()
    {

        return 0;
    }

    private int payrangers()
    {
        return rangers.size() * 50;

    }

    private boolean isGameOver()
    {
        return false;
    }

    public void ChangeSpeed(int speed)
    {
        this.speed = speed;
    }

    public void ChangeTicketPrice(int ticketprice)
    {
        this.ticketprice = ticketprice;
    }

    private int sumanimals( )
    {
        int sum = 0;
        for (Herd herd : herds)
        {
            sum += herd.animalcount();
        }

        return sum;
    }

    private int sumuniqueanimals() //ArrayList<Herd> herds
    {


        int sum = 0;

        ArrayList<AnimalSpecies> uniqueanimals = new ArrayList<>();

        for (Herd herd : herds)
        {
            if (!uniqueanimals.contains(herd.getAnimalSpecies())) {
                uniqueanimals.add(herd.getAnimalSpecies());
                sum++;
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

    public void BuyItem(ShopItem item, float x,float y,int width, int height)
    {
        money = money - item.getPrice();
        System.out.println(item.getName());

        switch(item.getName())
        {
            case "Capybara":
                AnimalFactory.createCapybara(new Position(x,y, width, height));  // Új Capybara példány létrehozása a megadott koordinátákkal
                System.out.println("Capybara buy successful!");
                break;
            case "Mammoth":
                break;
            case "Dinosaur":
                break;
            case "Lion":
                break;
            default: System.out.println("Not Implemented yet!");
            break;
        }
    }




    /*
    private CheckInRange()
    {

    }
    */
}
