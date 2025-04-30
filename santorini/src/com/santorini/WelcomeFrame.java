
package com.santorini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

//import static sun.tools.jconsole.inspector.XDataViewer.dispose;


public class WelcomeFrame  {


    private JPanel panel1;
    private JButton btn_Enter;

    public WelcomeFrame() {
        // Initialize the panel
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        
        // Initialize the button
        btn_Enter = new JButton("Enter Game");
        btn_Enter.setFont(new Font("Arial", Font.BOLD, 18));
        btn_Enter.setPreferredSize(new Dimension(200, 50));
        
        // Add the button to the panel (centered)
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btn_Enter);
        panel1.add(buttonPanel, BorderLayout.CENTER);
        
        // Add a welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Santorini", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel1.add(welcomeLabel, BorderLayout.NORTH);
        
        // Add the action listener to the button
        btn_Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanelFrame gamePanelFrame = new GamePanelFrame();
                gamePanelFrame.setVisible(true);
            }
        });
    }
    public static void main(String[]args){
        JFrame newFrame = new JFrame("Welcome");
        newFrame.setContentPane(new WelcomeFrame().panel1);
        newFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newFrame.setVisible(true);
        newFrame.setSize(1000, 1000);

    }
}

