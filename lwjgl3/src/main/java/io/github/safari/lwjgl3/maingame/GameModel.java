package io.github.safari.lwjgl3.maingame;





import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.Animal;
import io.github.safari.lwjgl3.positionable.npc.animals.AnimalFactory;
import io.github.safari.lwjgl3.positionable.npc.animals.Herd;
import io.github.safari.lwjgl3.positionable.npc.human.Poacher;
import io.github.safari.lwjgl3.positionable.npc.human.Ranger;
import io.github.safari.lwjgl3.positionable.npc.security.Security;
import io.github.safari.lwjgl3.positionable.objects.Environment;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;

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

        ArrayList<Animal> uniqueanimals = new ArrayList<>();

        for (Herd herd : herds)
        {
        }

        return 0;
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
