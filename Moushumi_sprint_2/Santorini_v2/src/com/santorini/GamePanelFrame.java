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
    private JLabel lb_player_1;
    private JLabel lb_player_2;
    private JLabel lb_card_1;
    private JLabel lb_card_2;
    private JLabel lb_winner;
    private JLabel win_pl_name;
    private JButton btn_finish;
    private JButton btn_start_over;
    private JLabel lb_worker;

    public GamePanelFrame(){
        this.setVisible(false);
        this.setSize(1000,1000);
        this.setContentPane(panel2);
        this.setTitle("Game Panel");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout(10,10);
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 10, 10);
        this.setLayout(borderLayout);
        panel_c.setLayout(new GridLayout(5,5,3,3));
        for (int i= 1; i<=25; i++){
            JLabel label = new JLabel(Integer.toString(i));
            label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            panel_c.add(label);
        }

        this.add(panel_n, BorderLayout.NORTH);
        this.add(panel_s, BorderLayout.SOUTH);
        this.add(panel_e, BorderLayout.EAST);
        this.add(panel_w, BorderLayout.WEST);
        this.add(panel_c, BorderLayout.CENTER);

        btn_finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dispose();
            }
        });
    }
    public static void main(String[]args){
        GamePanelFrame gamePanelFrame = new GamePanelFrame();
        gamePanelFrame.setVisible(true);
    }
}

// Ref: https://www.youtube.com/watch?v=1G4lBJW1vfM
//Ref: https://www.youtube.com/watch?v=4PfDdJ8GFHI
// https://stackoverflow.com/questions/5921175/how-to-set-jpanels-width-and-height


