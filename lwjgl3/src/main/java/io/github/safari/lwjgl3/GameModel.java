import lwjgl3.src.main.java.io.github.safari.lwjgl3.npc.animals.Herd;
import java.util.*;
import io.github.safari.lwjgl3.npc.animals.*;


public class GameModel {
    private int money;
    private int income;
    private int monthlyexpense;
    private int speed; // 1 - 3
    private int difficulty; // 1 - 3
    //map?
    private int ticketprice;
    private int touristcount;
    private ArrayList<Herd> herds;
    private ArrayList<Poaccher> poachers;
    private ArrayList<Ranger> rangers;
    private ArrayList<Jeep> jeeps;
    private ArrayList<Security> securities;
    private ArrayList<Environment> environments;



    public GameModel(int difficulty)
    {
        this.difficulty = difficulty;

    }

    public void InitializeGame()
    {

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

    public void calculateIncome(herbs,predators)
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

        Set<Animal> animals;

        for (herd : herds)
        {

        }

        return Seenanimals.size();
    }

    private CheckInRange()
    {

    }

}
