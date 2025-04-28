package com.santorini;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame {


    private JPanel panel1;
    private JButton btn_Enter;

    public WelcomeFrame() {
        btn_Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Log in success" );
            }
        });
    }

    public static void main(String[]args){
        JFrame newFrame = new JFrame("Login");
        newFrame.setContentPane(new WelcomeFrame().panel1);
        newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newFrame.setVisible(true);
        newFrame.setSize(500, 500);

    }
}
