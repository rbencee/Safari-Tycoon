package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.positionable.npc.Humans.Poacher;
import io.github.safari.lwjgl3.positionable.npc.Humans.Ranger;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.Visitors.*;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.security.*;

import java.util.ArrayList;
import java.util.Random;


public class GameModel {
    private int money;
    private int income;
    private int monthlyexpense;
    private int speed; // 1 - 3
    private int difficulty; // 1 - 3
    private int dayspassed;

    //map?
    private int ticketprice;
    private int touristcount;
    private ArrayList<Herbivore> herbs;
    private ArrayList<Herd> herds;
    private ArrayList<Poacher> poachers;
    private ArrayList<Ranger> rangers;
    private ArrayList<Jeep> jeeps;
    private ArrayList<Security> securities;
    private ArrayList<Environment> environments;
    private int objectNumber = 10;
    private float mapWidth = 3200;
    private float mapHeight = 3200;
    private Random random;
    private float minDistance;



    public GameModel(int difficulty)
    {
        this.difficulty = difficulty;
        this.random = new Random();

        environments = new ArrayList<>();
        minDistance = 50;

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

            if (positionFound(x, y, minDistance)) {
                environments.add(new Tree(x, y));
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;

            if (positionFound(x, y, minDistance)) {
                environments.add(new Bush(x, y));
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;
            if (positionFound(x, y, minDistance)) {
                environments.add(new Lake(x, y));
                objectCount++;
            }
        }

        objectCount = 0;
        while (objectCount < objectNumber) {
            float x = random.nextInt((int)(mapWidth / 32)) * 32;
            float y = random.nextInt((int)(mapHeight / 32)) * 32;


            if (positionFound(x, y, minDistance)) {
                environments.add(new Grass(x, y));
                objectCount++;
            }
        }

    }

    private boolean positionFound(float x, float y,float minDistance){
        for(Environment environment : environments){
            float distance = (float) Math.sqrt(Math.pow(x - environment.getX(), 2) + Math.pow(y - environment.getY(), 2));
            if(distance < minDistance){
                return false;
            }
        }
        return true;
    }

    public void Simulation()
    {


    }

    public void increasemoney(int money)
    {
        this.money += money;
    }

    public void calculateIncome()
    {


    }

    private int CalculateTourist()
    {

        return 0;
    }

    private void Payrangers(ArrayList<Ranger> rangers)
    {
        //Ki kell talalni, hogy mennyivel csokkentjuk

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

    private int Sumanimals( )//Kell herdbe egy cucc, ammi visszaadja hogy hany allat van benne
    {
        int sum = 0;
        for (Herd herd : herds)
        {
            sum += herd.animalcount(); //Lehet meg kell nezni hogy biztos el e.
        }

        return sum;
    }

    private int sumuniqueanimals(ArrayList<Herd> herds)
    {
        //To DO, enum lista, amiben szamontartjuk hogy bennevan e?

        ArrayList<Animal> uniqueanimals = new ArrayList<Animal>();

        for (Herd herd : herds)
        {
        }

        return 0;
    }

    public int sumHerbivorous() { //ArrayList<Herd> herds
        int sum = 0;

        for (Herd e : herds) {
            if (e.getIsherbivore()) {
                sum++;
            }
        }

        return sum;
    }

    public int sumPredators() //ArrayList<Herd> herds
    {
        int sum = 0;

        for (var e : herds) {
            if (!e.getIsherbivore()) {
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

    public void BuyItem(ShopItem item, int x,int y)
    {
        money = money - item.getPrice();

        switch(item.getName())
        {
            case "Capybara":
                Capybara capybara = new Capybara(x, y);  // Új Capybara példány létrehozása a megadott koordinátákkal
                Herd herd = new Herd(capybara, true);  // Új herd, a capybara lesz a vezető
                herds.add(herd);  // A herd hozzáadása a herds listához
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
