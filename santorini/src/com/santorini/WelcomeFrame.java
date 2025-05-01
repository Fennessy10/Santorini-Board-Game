package com.santorini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Welcome Frame for Santorini Game
 * Displays the intro screen and allows users to start the game
 */
public class WelcomeFrame {
    private JPanel panel1;
    private JButton btn_Enter;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel developerLabel;
    private JFrame parentFrame;

    public WelcomeFrame(JFrame newFrame) {
        this.parentFrame = newFrame;
        initializeUI();
    }

    private void initializeUI() {
        // Setup main panel
        panel1= new JPanel();
        panel1.setLayout(new GridLayout(4, 1));
        panel1.setBackground(new Color(146, 155, 37)); // -8900219 in RGB

        // Title label
        titleLabel = new JLabel("Welcome to Santorini", SwingConstants.CENTER);
        titleLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
        titleLabel.setForeground(new Color(240, 241, 0)); // -14414800 in RGB

        // Subtitle label
        subtitleLabel = new JLabel("Basic Santorini Game for beginners", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
        subtitleLabel.setForeground(new Color(240, 241, 0)); // -14414800 in RGB

        // Developer label
        developerLabel = new JLabel("Developed by : Team 024", SwingConstants.CENTER);
        developerLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
        developerLabel.setForeground(new Color(240, 241, 0)); // -14414800 in RGB

        // Enter button
        btn_Enter= new JButton("Enter");
        btn_Enter.setFont(new Font("Arial Black", Font.PLAIN, 14));
        btn_Enter.setBackground(new Color(158, 159, 145)); // -6446511 in RGB

        // Center the button in its own panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(146, 155, 37)); // Same as main panel
        buttonPanel.add(btn_Enter);

        // Add components to the main panel
        panel1.add(titleLabel);
        panel1.add(subtitleLabel);
        panel1.add(developerLabel);
        panel1.add(buttonPanel);

        // Add action listener to the Enter button
        btn_Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create game controller and initialize players
                GameController gameController = new GameController("John", "James");

                // Create and show game panel frame
                GamePanelFrame gamePanelFrame = new GamePanelFrame(gameController, null);
                gamePanelFrame.setVisible(true);
                gamePanelFrame.setLocationRelativeTo(null);
                gamePanelFrame.startGameThread();
                parentFrame.dispose();
            }
        });
    }

    /**
     * Get the main panel
     *
     * @return JPanel main panel
     */
    public JPanel getPanel1() {
        return panel1;
    }

    /**
     * Main method to run the Welcome Frame
     */
    public static void main(String[] args) {
        try {
            // Set system look and feel for better appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and configure parent frame
                JFrame newFrame = new JFrame("Santorini - Welcome");
                WelcomeFrame welcomeFrame = new WelcomeFrame(newFrame);

                // Set frame properties
                newFrame.setContentPane(welcomeFrame.getPanel1());
                newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                newFrame.setSize(800, 600);
                newFrame.setLocationRelativeTo(null); // Center on screen
                newFrame.setVisible(true);
            }
        });
    }
}