package com.example.minesweeper.model;

import java.util.Random;

public class Game {

    public static final char MINE = '*';
    public static final char COVERED_CELL = '.';
    public static final char UNCOVERED_CELL = ' ';
    public static final char FLAG_MARK = 'x';
    public static final char QUESTION_MARK = '?';

    private String player;

    private String id;

    private int rows;

    private int columns;

    private char[][] cellMines; // numbers from 0 to 8 or a '*' sign that represents a mine

    private char[][] cellState; // ' ' for uncovered cells, '.' for hidden cells, 'x' for mine marks, '?' for question marks*/

    public Game(String player, int rows, int columns, int mines) {
        this.player = player;
        this.rows = rows;
        this.columns = columns;

        if(mines > rows * columns) {
            throw new RuntimeException("Invalid amount of mines");
        }

        cellMines = new char[rows][columns];
        cellState = new char[rows][columns];

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < columns; y++) {
                cellMines[x][y] = '0';
                cellState[x][y] = COVERED_CELL;
            }
        }
        Random random = new Random();
        for(int placedMines = 0; placedMines < mines; placedMines++) {
            int x = random.nextInt(rows);
            int y = random.nextInt(columns);
            while(cellState[x][y] == '*') {
                x++;
                if(x == rows) {
                    x = 0;
                    y = (y + 1) % columns;
                }
            }
            cellMines[x][y] = '*';
            for (int adjacentX = x - 1; adjacentX <= x + 1; adjacentX++) {
                for (int adjacentY = y - 1; adjacentY <= y + 1; adjacentY++) {
                    if (adjacentX >= 0 && adjacentX < rows && adjacentY >= 0 && adjacentY < columns) {
                        if (cellMines[adjacentX][adjacentY] != MINE) {
                            cellMines[adjacentX][adjacentY]++;
                        }
                    }
                }
            }
        }
    }

    public String getPlayer() {
        return player;
    }

    public String getId() {
        return id;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public char[][] getCellMines() {
        return cellMines;
    }

    public char[][] getCellState() {
        return cellState;
    }

    /**
     * Uncovers a the cell in the position (x, y)
     *
     * @param x row
     * @param y column
     * @return true if it was a valid action, false if the player uncovered a mine
     */
    public boolean uncoverCell(int x, int y) {
        cellState[x][y] = UNCOVERED_CELL;
        if(cellMines[x][y] == MINE) {
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    cellState[x][y] = UNCOVERED_CELL;
                }
            }
            return false;
        }
        else {
            if(cellMines[x][y] == 0) {
                for (int adjacentX = x - 1; adjacentX <= x + 1; adjacentX++) {
                    for (int adjacentY = y - 1; adjacentY <= y + 1; adjacentY++) {
                        if (adjacentX >= 0 && adjacentX < rows && adjacentY >= 0 && adjacentY < columns) {
                            if (cellState[adjacentX][adjacentY] != UNCOVERED_CELL) {
                                if(!uncoverCell(x, y)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Adds a flag mark in the position (x, y)
     * @param x row
     * @param y column
     */
    public void addFlagMark(int x, int y) {
        if (cellState[x][y] != UNCOVERED_CELL) {
            cellState[x][y] = FLAG_MARK;
        }
    }

    /**
     * Adds a question mark in the position (x, y)
     * @param x row
     * @param y column
     */
    public void addQuestionMark(int x, int y) {
        if (cellState[x][y] != UNCOVERED_CELL) {
            cellState[x][y] = QUESTION_MARK;
        }
    }
}
