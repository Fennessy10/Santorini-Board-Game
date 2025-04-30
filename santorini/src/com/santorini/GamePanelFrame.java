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
        // Initialize main panels
        panel2 = new JPanel(new BorderLayout(10, 10));
        panel_n = new JPanel();
        panel_w = new JPanel();
        panel_e = new JPanel();
        panel_s = new JPanel();
        panel_c = new JPanel();
        
        // Initialize labels
        lb_player_1 = new JLabel("Player 1");
        lb_player_2 = new JLabel("Player 2");
        lb_card_1 = new JLabel("Card 1");
        lb_card_2 = new JLabel("Card 2");
        lb_winner = new JLabel("Winner:");
        win_pl_name = new JLabel("");
        lb_worker = new JLabel("Worker");
        
        // Initialize buttons
        btn_finish = new JButton("Finish");
        btn_start_over = new JButton("Start Over");
        
        // Set up the frame
        this.setVisible(false);
        this.setSize(1000, 1000);
        this.setContentPane(panel2);
        this.setTitle("Game Panel");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Configure panel borders for visibility
        panel_n.setBorder(BorderFactory.createTitledBorder("North Panel"));
        panel_w.setBorder(BorderFactory.createTitledBorder("West Panel"));
        panel_e.setBorder(BorderFactory.createTitledBorder("East Panel"));
        panel_s.setBorder(BorderFactory.createTitledBorder("South Panel"));
        panel_c.setBorder(BorderFactory.createTitledBorder("Center Panel"));
        
        // Set up panel layouts
        panel_n.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel_w.setLayout(new BoxLayout(panel_w, BoxLayout.Y_AXIS));
        panel_e.setLayout(new BoxLayout(panel_e, BoxLayout.Y_AXIS));
        panel_s.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel_c.setLayout(new GridLayout(5, 5, 3, 3));
        
        // Populate the north panel
        panel_n.add(lb_player_1);
        panel_n.add(lb_player_2);
        
        // Populate the west panel
        panel_w.add(lb_card_1);
        panel_w.add(Box.createVerticalStrut(10));
        panel_w.add(lb_worker);
        
        // Populate the east panel
        panel_e.add(lb_card_2);
        
        // Populate the south panel
        panel_s.add(lb_winner);
        panel_s.add(win_pl_name);
        panel_s.add(btn_finish);
        panel_s.add(btn_start_over);
        
        // Populate the center panel with grid cells
        for (int i = 1; i <= 25; i++){
            JLabel label = new JLabel(Integer.toString(i));
            label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(50, 50));
            panel_c.add(label);
        }

        // Add all panels to the main container
        panel2.add(panel_n, BorderLayout.NORTH);
        panel2.add(panel_s, BorderLayout.SOUTH);
        panel2.add(panel_e, BorderLayout.EAST);
        panel2.add(panel_w, BorderLayout.WEST);
        panel2.add(panel_c, BorderLayout.CENTER);

        // Add action listener to the finish button
        btn_finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Add action listener to the start over button
        btn_start_over.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset game logic here
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


