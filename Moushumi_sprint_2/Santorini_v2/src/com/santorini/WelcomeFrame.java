
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

