package com.santorini;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanelFrame extends JFrame{
    private JPanel panel2;
    private JPanel panel_n;
    private JPanel panel_w;
    private JPanel panel_e;
    private JPanel panel_s;
    private JPanel panel_c;

    public GamePanelFrame(){
        this.setVisible(false);
        this.setSize(1000,1000);
        this.setContentPane(panel2);
        this.setTitle("Game Panel");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout(10,10);
        this.setLayout(borderLayout);
        this.add(panel_n, BorderLayout.NORTH);
        this.add(panel_w, BorderLayout.SOUTH);
        this.add( panel_e, BorderLayout.EAST);
        this.add(panel_s, BorderLayout.WEST);
        this.add(panel_c, BorderLayout.CENTER);
    }
    public static void main(String[]args){
        GamePanelFrame gamePanelFrame = new GamePanelFrame();
        gamePanelFrame.setVisible(true);
    }
}

// Ref: https://www.youtube.com/watch?v=1G4lBJW1vfM
//Ref: https://www.youtube.com/watch?v=4PfDdJ8GFHI


