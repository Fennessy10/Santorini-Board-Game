package main;

import javax.swing.*;

public class Main {
    public static void main(String[]args){

        JFrame window = new JFrame("Login");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // window is sized to fit the size of game panel

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
