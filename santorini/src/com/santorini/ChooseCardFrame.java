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
    private JComboBox<String> cardCombo;
    private JButton btnNext;
    private JFrame parentFrame;
    private String p1_name;
    private String p2_name;
    private int p1_age;
    private int p2_age;
    private HashMap<String, GodCard> godCardMap;

    public ChooseCardFrame(JFrame parentFrame, String p1_name, int p1_age, String p2_name, int p2_age) {
        this.parentFrame = parentFrame;
        this.p1_name = p1_name;
        this.p2_name = p2_name;
        this.p1_age = p1_age;
        this.p2_age = p2_age;

        setTitle("Choose God Card - " + p1_name);
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
        panel.add(new JLabel(p1_name + ", choose your God Card:"));
        cardCombo = new JComboBox<>(godNames);
        panel.add(cardCombo);

        btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected1 = (String) cardCombo.getSelectedItem();
                GodCard godCard1 = godCardMap.get(selected1);

                ChooseCardFrame2 secondFrame = new ChooseCardFrame2(
                        parentFrame, p1_name, p1_age, p2_name, p2_age, selected1, godCard1
                );
                secondFrame.setVisible(true);
                dispose();
            }
        });

        add(panel, BorderLayout.CENTER);
        add(btnNext, BorderLayout.SOUTH);
    }
}
