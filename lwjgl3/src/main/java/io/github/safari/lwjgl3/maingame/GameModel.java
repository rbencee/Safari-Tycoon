package lwjgl3.src.main.java.io.github.safari.lwjgl3.maingame;

import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.npc.animals.Herbivore;
import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.npc.*;
import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.Visitors.*;
import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.security.*;
import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.objects.*;




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

    public void calculateIncome(herds,herbs)
    {


    }

    private int CalculateTourist(int ticket, ArrayList<Herd>)
    {


    }

    private void Payrangers(ArrayList<Ranger> rangers)
    {
        //Ki kell talalni, hogy mennyivel csokkentjuk

    }

    private boolean isGameOver()
    {

    }

    public void ChangeSpeed(int speed)
    {
        this.speed = speed;
    }

    public ChangeTicketPrice(int ticketprice)
    {
        this.ticketprice = ticketprice;
    }

    private Sumanimals(ArrayList<Herd> herds) //Kell herdbe egy cucc, ammi visszaadja hogy hany allat van benne
    {
        sum = 0;
        for (herd : herds)
        {
            sum += herd.animalcount(); //Lehet meg kell nezni hogy biztos el e.
        }

        return sum;
    }

    private sumuniqueanimals(ArrayList<Herd> herds)
    {
        //To DO, enum lista, amiben szamontartjuk hogy bennevan e?

        Animal ArrayList<Animal> uniqueanimals = new ArrayList<Animal>();

        for (herd : herds)
        {
            if()
        }

        return Seenanimals.size();
    }

    public int sumHerbivorous() { //ArrayList<Herd> herds
        int sum = 0;

        for (var e : herds) {
            if (e.getIsherbivore) {
                sum++;
            }
        }

        return sum;
    }

    public int sumPredators() //ArrayList<Herd> herds
    {
        int sum = 0;

        for (var e : herds) {
            if (!e.getIsherbivore) {
                sum++;
            }
        }

        return sum;


    }



    private CheckInRange()
    {

    }

}
