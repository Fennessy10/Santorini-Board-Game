package com.santorini;

import com.santorini.gods.ArtemisCard;
import com.santorini.gods.DemeterCard;
import com.santorini.gods.ZeusCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ChooseCardFrame2 extends JFrame {
    private JComboBox<String> cardCombo;
    private JButton btnStartGame;
    private JFrame parentFrame;
    private String p1_name, p2_name;
    private int p1_age, p2_age;
    private String p1_cardName;
    private GodCard godCard1;
    private HashMap<String, GodCard> godCardMap;

    public ChooseCardFrame2(JFrame parentFrame, String p1_name, int p1_age, String p2_name, int p2_age, String p1_cardName, GodCard godCard1) {
        this.parentFrame = parentFrame;
        this.p1_name = p1_name;
        this.p2_name = p2_name;
        this.p1_age = p1_age;
        this.p2_age = p2_age;
        this.p1_cardName = p1_cardName;
        this.godCard1 = godCard1;

        setTitle("Choose God Card - " + p2_name);
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] godNames = {"Artemis", "Demeter", "Zeus"};
        godCardMap = new HashMap<>();
        godCardMap.put("Artemis", new ArtemisCard());
        godCardMap.put("Demeter", new DemeterCard());
        godCardMap.put("Zeus", new ZeusCard());

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.add(new JLabel(p2_name + ", choose your God Card:"));
        cardCombo = new JComboBox<>(godNames);
        panel.add(cardCombo);

        btnStartGame = new JButton("Start Game");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected2 = (String) cardCombo.getSelectedItem();

                if (selected2.equals(p1_cardName)) {
                    JOptionPane.showMessageDialog(
                            ChooseCardFrame2.this,
                            "You must choose a different God Card from " + p1_name,
                            "Invalid Selection",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                GodCard godCard2 = godCardMap.get(selected2);

                GameController controller = new GameController(
                        p1_name, p2_name, p1_age, p2_age, godCard1, godCard2
                );

                GamePanelFrame gamePanel = new GamePanelFrame(controller);
                controller.setGamePanel(gamePanel);
                gamePanel.setVisible(true);
                gamePanel.setLocationRelativeTo(null);
                gamePanel.startGameThread();

                parentFrame.dispose();
                dispose();
            }
        });

        add(panel, BorderLayout.CENTER);
        add(btnStartGame, BorderLayout.SOUTH);
    }
}
