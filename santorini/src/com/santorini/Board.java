package com.santorini;

public class Board {
    // attribute for board double array bcuz grid of cells = board
    private Cell[][] cells;
    /* NOTE: ideally we should have this in a configuration file for extensibility
        although we need to prioritise functionality at this point. This won't be hard to move
     */
    private static final int BOARD_SIZE = 5;

    // board constructor
    public Board() {
        initBoard();
    }

    // init method for board
    private void initBoard() {
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        // nested for loop to initialise cells in the board
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    // grabs cell from the board based on the given coordinates
    public Cell grabCell(int x, int y) {
        if (isPosAllowed(x, y)) {
            return cells[x][y];
        }
        return null;
    }

    // checks if coords are within bounds of board
    public boolean isPosAllowed(int x, int y) {
        return x<BOARD_SIZE&&y<BOARD_SIZE&&x>=0&&y>=0;
    }


}
