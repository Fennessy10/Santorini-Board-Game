package com.santorini;

import com.santorini.gods.ArtemisCard;
import com.santorini.gods.DemeterCard;
import com.santorini.gods.ZeusCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ChooseCardFrame extends JFrame {
    private JComboBox<String> player1CardCombo;
    private JComboBox<String> player2CardCombo;
    private JButton btnEnter;
    private JFrame parentFrame;
    private String p1_name;
    private String p2_name;
    private int p1_age;
    private int p2_age;

    public ChooseCardFrame(JFrame parentFrame, String p1_name, int p1_age, String p2_name, int p2_age) {
        this.parentFrame = parentFrame;
        this.setTitle("Choose Card");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.p1_age = p1_age;
        this.p2_age = p2_age;
        this.p1_name = p1_name;
        this.p2_name = p2_name;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Choose God Cards");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // GodCard options
        String[] godNames = {"Artemis", "Demeter", "Zeus"};
        HashMap<String, GodCard> godCardMap = new HashMap<>();
        godCardMap.put("Artemis", new ArtemisCard());
        godCardMap.put("Demeter", new DemeterCard());
        godCardMap.put("Zeus", new ZeusCard());

        // Setup UI
        JPanel selectionPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        selectionPanel.add(new JLabel(p1_name + " God Card:"));
        player1CardCombo = new JComboBox<>(godNames);
        selectionPanel.add(player1CardCombo);

        selectionPanel.add(new JLabel(p2_name + " God Card:"));
        player2CardCombo = new JComboBox<>(godNames);
        selectionPanel.add(player2CardCombo);

        btnEnter = new JButton("Confirm");

        // Button Action
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected1 = (String) player1CardCombo.getSelectedItem();
                String selected2 = (String) player2CardCombo.getSelectedItem();

                if (selected1.equals(selected2)) {
                    JOptionPane.showMessageDialog(
                            ChooseCardFrame.this,
                            "Each player must choose a different God Card",
                            "Invalid Selection",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                GodCard godCard1 = godCardMap.get(selected1);
                GodCard godCard2 = godCardMap.get(selected2);

                // Assuming WelcomeFrame or main game provides player names and ages
                String player1Name = "Player 1"; // Replace as needed
                String player2Name = "Player 2";
                int player1Age = 20;
                int player2Age = 22;

                // Create game controller with god cards
                GameController controller = new GameController(
                        player1Name, player2Name, player1Age, player2Age, godCard1, godCard2
                );

                GamePanelFrame gamePanel = new GamePanelFrame(controller, null);
                controller.setGamePanel(gamePanel);
                gamePanel.setVisible(true);
                gamePanel.startGameThread();
                gamePanel.setLocationRelativeTo(null);

                parentFrame.dispose(); // Close welcome screen
                dispose(); // Close this frame
            }
        });

        add(selectionPanel, BorderLayout.CENTER);
        add(btnEnter, BorderLayout.SOUTH);
    }
}
