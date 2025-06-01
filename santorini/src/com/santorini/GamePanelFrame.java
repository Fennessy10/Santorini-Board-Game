package com.santorini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.border.LineBorder;

public class GamePanelFrame extends JFrame implements Runnable {
    // file path for saving and loading game
    private final String saveFileName= "santorini_save.txt";

    // UI Components from original GamePanelFrame
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
    private JButton btn_save_game;
    private JButton btn_load_game;

    // Screen settings
    private final int originalTileSize = 16;
    private final int scale = 6;
    private final int tileSize = originalTileSize * scale;
    private final int CELL_SIZE = tileSize; // Using tileSize as cell size

    // Board size
    private final int BOARD_SIZE = 5;
    private final int screenWidth = CELL_SIZE * BOARD_SIZE;
    private final int screenHeight = CELL_SIZE * BOARD_SIZE;

    // FPS
    private final int FPS = 60;
    private Thread gameThread;

    // Colours for gamephases
    private final Color darkGrey2 = new Color(120, 120, 120);
    private final Color darkGrey3 = new Color(168, 168, 168);

    // Game components from GamePanel
    private GameController controller;
    private Controls controls;

    // Status labels for game information
    private JLabel statusLabel;
    private JLabel currentPlayerLabel;


    public GamePanelFrame() {
        initializeUI();
    }


    public GamePanelFrame(GameController controller) {
        this.controller = controller;
        this.controls = new Controls(this);

        initializeUI();

        // Add mouse listener for board interaction
        panel_c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCellClick(e.getX(), e.getY());
            }
        });
    }


    private void initializeUI() {
        this.setPreferredSize(new Dimension(screenWidth + 300, screenHeight + 200)); // Added space for panels
        this.setBackground(Color.black);
        this.setFocusable(true); // Focused to receive key input

        // Initialize main panels
        panel2 = new JPanel(new BorderLayout(10, 10));
        panel_n = new JPanel();
        panel_w = new JPanel();
        panel_e = new JPanel();
        panel_s = new JPanel();

        // Create the center panel with custom painting
        panel_c = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (controller != null) {
                    drawBoard(g);
                } else {
                    // Draw placeholder grid when controller is not available
                    drawPlaceholderGrid(g);
                }
            }
        };

        // Initialize labels
        lb_player_1 = new JLabel("Player 1");
        lb_player_2 = new JLabel("Player 2");
        lb_card_1 = new JLabel("Card 1");
        lb_card_2 = new JLabel("Card 2");
        lb_winner = new JLabel("Winner:");
        win_pl_name = new JLabel("");
        lb_worker = new JLabel("Worker");

        // Game status labels
        statusLabel = new JLabel("Game Phase: Setup");
        currentPlayerLabel = new JLabel("Current Player: None");

        // Initialize buttons
        btn_finish = new JButton("Finish");
        btn_start_over = new JButton("Start Over");
        btn_save_game = new JButton("Save Game");
        btn_load_game = new JButton("Load Game");

        // Set up the frame
        this.setSize(800, 800);
        this.setContentPane(panel2);
        this.setTitle("Santorini Game");
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Configure panel borders for visibility
        panel_n.setBorder(BorderFactory.createTitledBorder("Game Info"));
        panel_w.setBorder(BorderFactory.createTitledBorder("Player 1"));
        panel_e.setBorder(BorderFactory.createTitledBorder("Player 2"));
        panel_s.setBorder(BorderFactory.createTitledBorder("Game Controls"));
        panel_c.setBorder(BorderFactory.createTitledBorder("Game Board"));

        // Set up panel layouts
        panel_n.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel_w.setLayout(new BoxLayout(panel_w, BoxLayout.Y_AXIS));
        panel_e.setLayout(new BoxLayout(panel_e, BoxLayout.Y_AXIS));
        panel_s.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel_c.setLayout(null); // Using null layout for custom drawing
        panel_c.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Populate the north panel
        panel_n.add(statusLabel);
        panel_n.add(currentPlayerLabel);

        // Populate the west panel
        panel_w.add(lb_player_1);
        panel_w.add(Box.createVerticalStrut(10));

        // Populate the east panel
        panel_e.add(lb_player_2);
        panel_e.add(Box.createVerticalStrut(10));

        // Populate the south panel
        panel_s.add(lb_winner);
        panel_s.add(win_pl_name);
        panel_s.add(btn_finish);
        panel_s.add(btn_start_over);
        panel_s.add(btn_save_game);
        panel_s.add(btn_load_game);

        // Wrap panel_c in a center wrapper to centre it properly
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBorder(BorderFactory.createEmptyBorder());
        centerWrapper.add(panel_c); // Add game board panel to the centre

        // Add all panels to the main container
        panel2.add(panel_n, BorderLayout.NORTH);
        panel2.add(panel_s, BorderLayout.SOUTH);
        panel2.add(panel_e, BorderLayout.EAST);
        panel2.add(panel_w, BorderLayout.WEST);
        panel2.add(centerWrapper, BorderLayout.CENTER);


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
                if (controller != null) {
                    // Reset game
                    controller.resetGame();
                    updateUI();
                }
            }
        });

        btn_save_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.saveGame(saveFileName);
                }
            }
        });

        btn_load_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.loadGame(saveFileName);
                }
            }
        });
    }

    private void drawPlaceholderGrid(Graphics g) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                int cellX = x * CELL_SIZE;
                int cellY = y * CELL_SIZE;

                // Draw cell background
                g.setColor(new Color(200, 230, 255));
                g.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);

                // Draw cell border
                g.setColor(Color.BLACK);
                g.drawRect(cellX, cellY, CELL_SIZE, CELL_SIZE);

                // Draw cell number
                g.setColor(Color.BLACK);
                Font originalFont = g.getFont();
                g.setFont(new Font(originalFont.getName(), Font.BOLD, 16));
                g.drawString((y * BOARD_SIZE + x + 1) + "", cellX + CELL_SIZE/2 - 10, cellY + CELL_SIZE/2 + 5);
                g.setFont(originalFont);
            }
        }
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
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
        if (controller != null) {
            updateUI();
        }
    }

    void updateUI() {
        if (controller != null) {
            // Update game info labels
            statusLabel.setText("Game Phase: " + controller.getGamePhase());
            if (controller.getGamePhase() == GamePhase.SELECT_WORKER) {
                statusLabel.setForeground(Color.BLACK);

            } else if (controller.getGamePhase() == GamePhase.MOVE_WORKER){
                statusLabel.setForeground(darkGrey2);

            } else if (controller.getGamePhase() == GamePhase.BUILD){
                statusLabel.setForeground(darkGrey3);

            } else {
                statusLabel.setForeground(Color.BLACK);
            }


            Player currentPlayer = controller.getCurrentPlayer();
            if (currentPlayer == controller.getPlayers().get(0)) {
                currentPlayerLabel.setForeground(Color.RED);
            } else {
                currentPlayerLabel.setForeground(Color.BLUE);
            }
            currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());


            // Update player info
            lb_player_1.setText(controller.getPlayers().get(0).getName());
            lb_player_2.setText(controller.getPlayers().get(1).getName());

            // Update god cards
            lb_card_1.setText("God: " + controller.getPlayers().get(0).getGodCard().getName());
            lb_card_2.setText("God: " + controller.getPlayers().get(1).getGodCard().getName());

            // Repaint the center panel
            panel_c.repaint();
        }
    }


    private void drawBoard(Graphics g) {
        Board board = controller.getBoard();

        // Draw cells
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Cell cell = board.grabCell(x, y);
                drawCell(g, cell);
            }
        }
    }

    private void drawCell(Graphics g, Cell cell) {
        int x = cell.getX() * CELL_SIZE;
        int y = cell.getY() * CELL_SIZE;

        // Draw cell background
        g.setColor(new Color(200, 230, 255));
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

        // Draw cell border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);

        // Draw building level
        int level = cell.getBuildLvl();
        if (level > 0) {
            drawBuildingLevel(g, x, y, level);
        }

        // Draw dome if present
        if (cell.getDome()) {
            drawDome(g, x, y);
        }

        // Draw worker if present
        if (cell.containsWorker()) {
            drawWorker(g, x, y, cell.getWorker());
        }
    }

    private void drawBuildingLevel(Graphics g, int x, int y, int level) {
        Color[] levelColors = {
                new Color(255, 235, 205), // Level 1: Light beige
                new Color(245, 222, 179), // Level 2: Darker beige
                new Color(235, 210, 160)  // Level 3: Even darker beige
        };

        if (level >= 1 && level <= 3) {
            g.setColor(levelColors[level - 1]);
            int margin = 5;
            int levelHeight = 15;

            // Draw a 3D-like block for the building level
            int blockX = x + margin;
            int blockY = y + margin;
            int blockWidth = CELL_SIZE - 2 * margin;
            int blockHeight = CELL_SIZE - 2 * margin;

            g.fillRect(blockX, blockY, blockWidth, blockHeight);

            // Draw level number
            g.setColor(Color.BLACK);
            Font originalFont = g.getFont();
            g.setFont(new Font(originalFont.getName(), Font.BOLD, 16));
            g.drawString(String.valueOf(level), x + CELL_SIZE/2 - 5, y + CELL_SIZE/2 + 5);
            g.setFont(originalFont);
        }
    }

    private void drawDome(Graphics g, int x, int y) {
        g.setColor(new Color(100, 180, 255)); // Blue dome
        int margin = 10;
        g.fillOval(x + margin, y + margin, CELL_SIZE - 2 * margin, CELL_SIZE - 2 * margin);
    }

    private void drawWorker(Graphics g, int x, int y, Worker worker) {
        Graphics2D g2d = (Graphics2D) g;
        Color workerColor;
        if (worker.getPlayer() == controller.getPlayers().get(0)) {
            workerColor = Color.RED;
        } else {
            workerColor = Color.BLUE;
        }
        int workerMargin = 15;
        int workerSize = CELL_SIZE - 2 * workerMargin;
        Player currentPlayer = controller.getCurrentPlayer();
        if (currentPlayer.getUtilisedWorker() == worker) {
            Stroke originalStroke = g2d.getStroke();
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(4.0f));
            g2d.drawOval(
                    x + workerMargin - 5,
                    y + workerMargin - 5,
                    workerSize + 10,
                    workerSize + 10
            );
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawOval(
                    x + workerMargin - 10,
                    y + workerMargin - 10,
                    workerSize + 20,
                    workerSize + 20
            );
            g2d.setStroke(originalStroke);
        }
        g2d.setColor(workerColor);
        g2d.fillOval(
                x + workerMargin,
                y + workerMargin,
                workerSize,
                workerSize
        );
        g2d.setColor(workerColor.darker());
        g2d.fillOval(
                x + workerMargin + 5,
                y + workerMargin + 5,
                workerSize - 10,
                workerSize - 10
        );
        g2d.setColor(Color.BLACK);
        g2d.drawOval(
                x + workerMargin,
                y + workerMargin,
                workerSize,
                workerSize
        );
        g2d.setColor(Color.WHITE);
        Font originalFont = g.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics metrics = g2d.getFontMetrics();
        String workerId = String.valueOf(worker.getWorkerId());
        int textX = x + CELL_SIZE/2 - metrics.stringWidth(workerId)/2;
        int textY = y + CELL_SIZE/2 + metrics.getHeight()/3;
        g2d.setColor(Color.BLACK);
        g2d.drawString(workerId, textX+1, textY+1);
        g2d.drawString(workerId, textX-1, textY-1);
        g2d.drawString(workerId, textX+1, textY-1);
        g2d.drawString(workerId, textX-1, textY+1);
        g2d.setColor(Color.WHITE);
        g2d.drawString(workerId, textX, textY);
        g2d.setFont(originalFont);
    }

    public void handleCellClick(int x, int y) {
        // Convert pixel coordinates to board coordinates
        int boardX = x / CELL_SIZE;
        int boardY = y / CELL_SIZE;

        // Ensure coordinates are within board boundaries
        if (boardX >= 0 && boardX < BOARD_SIZE && boardY >= 0 && boardY < BOARD_SIZE) {
            controller.processCellClick(boardX, boardY);
            updateUI();
        }
    }

    public void showWinMessage(String playerName) {
        SwingUtilities.invokeLater(() -> {
            win_pl_name.setText(playerName);
            JOptionPane.showMessageDialog(this,
                    playerName + " has won the game!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
    public void setController(GameController controller) {
        this.controller = controller;
        updateUI();
    }

    public Controls getControls() {
        return controls;
    }


    public GameController getController() {
        return controller;
    }





}