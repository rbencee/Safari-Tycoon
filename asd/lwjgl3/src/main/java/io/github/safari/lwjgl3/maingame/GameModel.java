package io.github.safari.lwjgl3.maingame;

import io.github.safari.lwjgl3.positionable.npc.Humans.Poacher;
import io.github.safari.lwjgl3.positionable.npc.Humans.Ranger;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.Visitors.*;
import io.github.safari.lwjgl3.positionable.security.*;
import io.github.safari.lwjgl3.positionable.objects.Environment;




import java.util.ArrayList;


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



    public GameModel(int difficulty)
    {
        this.difficulty = difficulty;
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

    public void InitializeGame()
    {



        generateMap();
    }

    private void generateMap()
    {


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


    /*
    private CheckInRange()
    {

    }
    */
}
