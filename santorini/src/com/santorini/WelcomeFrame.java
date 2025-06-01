package com.santorini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;


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
        panel1.setLayout(new GridLayout(5, 1));
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

        // Player 1 name's input
        JPanel namePanel = new JPanel();
        namePanel.setBackground(new Color(146, 155, 37)); // Same as main panel
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel nameLabel = new JLabel("Enter Player 1 Name: ");
        nameLabel.setFont(new Font("DialogInput", Font.BOLD, 16));
        nameLabel.setForeground(new Color(240, 241, 0)); // Yellowish

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("DialogInput", Font.PLAIN, 16));
        nameField.setColumns(15);
        nameField.setPreferredSize(new Dimension(200, 30));  // Force a visible size
        nameField.setBackground(Color.WHITE); // Ensure it's visible
        nameField.setForeground(Color.BLACK); // Text color
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JLabel name2Label = new JLabel("Enter Player 2 Name: ");
        name2Label.setFont(new Font("DialogInput", Font.BOLD, 16));
        name2Label.setForeground(new Color(240, 241, 0)); // Yellowish

        JTextField name2Field = new JTextField();
        name2Field.setFont(new Font("DialogInput", Font.PLAIN, 16));
        name2Field.setColumns(15);
        name2Field.setPreferredSize(new Dimension(200, 30));  // Force a visible size
        name2Field.setBackground(Color.WHITE); // Ensure it's visible
        name2Field.setForeground(Color.BLACK); // Text color
        namePanel.add(name2Label);
        namePanel.add(name2Field);

        JLabel ageLabel = new JLabel("     Enter Player 1 Age: ");
        ageLabel.setFont(new Font("DialogInput", Font.BOLD, 16));
        ageLabel.setForeground(new Color(240, 241, 0)); // Yellowish

        JTextField ageField = new JTextField();
        ageField.setFont(new Font("DialogInput", Font.PLAIN, 16));
        ageField.setColumns(15);
        ageField.setPreferredSize(new Dimension(200, 30));  // Force a visible size
        ageField.setBackground(Color.WHITE); // Ensure it's visible
        ageField.setForeground(Color.BLACK); // Text color
        namePanel.add(ageLabel);
        namePanel.add(ageField);

        JLabel ageLabel2 = new JLabel("Enter Player 2 Age: ");
        ageLabel2.setFont(new Font("DialogInput", Font.BOLD, 16));
        ageLabel2.setForeground(new Color(240, 241, 0)); // Yellowish

        JTextField ageField2 = new JTextField();
        ageField2.setFont(new Font("DialogInput", Font.PLAIN, 16));
        ageField2.setColumns(15);
        ageField2.setPreferredSize(new Dimension(200, 30));  // Force a visible size
        ageField2.setBackground(Color.WHITE); // Ensure it's visible
        ageField2.setForeground(Color.BLACK); // Text color

        // Apply integer-only filter
        ((AbstractDocument) ageField.getDocument()).setDocumentFilter(new IntegerFilter());
        ((AbstractDocument) ageField2.getDocument()).setDocumentFilter(new IntegerFilter());

        namePanel.add(ageLabel2);
        namePanel.add(ageField2);


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
        panel1.add(namePanel);
        panel1.add(buttonPanel);

        // Add action listener to the Enter button
        btn_Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String player1Name = ageField.getText().trim();
                String player2Name = ageField2.getText().trim();

                if (player1Name.equals("") || player2Name.equals("")) {
                    player1Name = "Player 1";
                    player2Name = "Player 2";
                }

                // Create game controller and initialize players
                GameController gameController = new GameController(player1Name, player2Name);

                // Create and show game panel frame
                GamePanelFrame gamePanelFrame = new GamePanelFrame(gameController, null);
                gameController.setGamePanel(gamePanelFrame); // call setter from game controller
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