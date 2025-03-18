package lwjgl3.src.main.java.io.github.safari.lwjgl3.maingame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu {

    private JFrame frame;
    private int selecteddifficulty;

    public int getSelecteddifficulty() {
        return selecteddifficulty;
    }

    public MainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame = frame;
        this.selecteddifficulty = 0;


        addbuttons();
        frame.setVisible(true);
    }

    private void addbuttons()
    {
        JButton buttonstart = new JButton("START GAME");
        buttonstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selecteddifficulty != 0) {
                    frame.setVisible(false);
                    JOptionPane.showMessageDialog(frame, "Kezdodik a jatek!");
                }else
                {
                    JOptionPane.showMessageDialog(frame, "Nem valasztottal nehezseget!");
                }
            }
        });

        JButton buttonexit = new JButton("EXIT GAME");
        buttonexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton buttoneasy = new JButton("Easy");
        buttoneasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecteddifficulty= 1;
            }
        });

        JButton buttonnormal = new JButton("Normal");
        buttoneasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecteddifficulty= 2;
            }
        });

        JButton buttonhard = new JButton("Hard");
        buttoneasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecteddifficulty= 3;
            }
        });


    }
}
