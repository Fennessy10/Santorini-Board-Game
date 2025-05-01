package com.santorini;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanelFrame extends JFrame implements Runnable {
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

    // screen settings
    private final int originalTileSize = 16;
    private final int scale = 8;

    private final int tileSize = originalTileSize * scale;

    // hardcoded panel size based on default 5x5 board Santorini normally uses
    private final int maxScreenCol = 5;
    private final int maxScreenRow = 5;

    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    // FPS
    int FPS = 60;

    main.Controls controls = new main.Controls();
    Thread gameThread;

    // set workers default position
    private int workerX = 0;
    private int workerY = 0;

    public GamePanelFrame(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addKeyListener(controls);
        this.addMouseListener(controls);
        this.addMouseMotionListener(controls);
        this.setFocusable(true); // focused to recieve key input

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

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS; // ~0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

    public void update() {
        // if worker clicked
        if (controls.MouseIsClicked()) {
            // light up the adjacent spaces around thw worker
            System.out.println("click again to stop");

        }
    }

//    public void paintComponent(Graphics g) {
//
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(Color.white);
//
//
//
//        g2.fillRect(0, 300, tileSize, tileSize);
//
//        g2.fillRect(workerX, workerY, tileSize, tileSize);
//
//
//        g2.dispose(); //release once done (safer + better performance)
//    }
    
    public static void main(String[]args){
        GamePanelFrame gamePanelFrame = new GamePanelFrame();
        gamePanelFrame.setVisible(true);

        gamePanelFrame.setLocationRelativeTo(null);

        GameController gameController = new GameController();
        gameController.initPlayers("john", "james");

        gamePanelFrame.startGameThread();

    }


}

// Ref: https://www.youtube.com/watch?v=1G4lBJW1vfM
//Ref: https://www.youtube.com/watch?v=4PfDdJ8GFHI
// https://stackoverflow.com/questions/5921175/how-to-set-jpanels-width-and-height


