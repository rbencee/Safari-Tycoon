package lwjgl3.src.main.java.io.github.safari.lwjgl3.maingame;

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameView {

    JFrame frame;
    JLabel scorepanel;
    GameModel gameModel;
    MainMenu mainMenu;
    private Timer timer;


    public GameView()
    {
        JFrame frame = new JFrame("Safari");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame = frame;
        mainMenu = new MainMenu();
        if(mainMenu.getSelecteddifficulty() != 0)
        {
            gameModel = new GameModel(mainMenu.getSelecteddifficulty());
            this.scorepanel = new JLabel("");
        }


    }

    public void UpdateScore()
    {

        int time = gameModel.getDayspassed();
        int income = gameModel.getIncome();
        int balance = gameModel.getMoney();
        int touristcount = gameModel.getTouristcount();
        int herbivorecount = gameModel.sumHerbivorous();
        int sumpredators = gameModel.sumPredators();

    }

    private void StartNewGame(){
        stop();

        gameModel.InitializeGame();

        timer = new Timer(10, oneGameCycleAction);
        timer.start();



    }

    private void stop(){
        if(timer != null && timer.isRunning()){
            timer.stop();
        }
    }

    private final ActionListener oneGameCycleAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){

        }
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 20));
        graphics2D.drawString("Tourists - " + gameModel.getTouristcount(), 10, 10);
        graphics2D.drawString("Herbs - " + gameModel.sumHerbivorous(), 40, 10);
        graphics2D.drawString("Predators - " + gameModel.sumPredators(), 70, 10);
        graphics2D.drawString("Income - " + gameModel.getIncome(), 100, 10);
        graphics2D.drawString("Balance - " + gameModel.getMoney(), 130, 10);


    }

}
