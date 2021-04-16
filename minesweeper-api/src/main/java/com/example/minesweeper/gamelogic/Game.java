package com.example.minesweeper.gamelogic;

import com.example.minesweeper.gamelogic.exceptions.InvalidGameSettingsException;

import java.util.Random;

public class Game {

    public static final String MINE = "*";
    public static final String COVERED_CELL = ".";
    public static final String UNCOVERED_CELL = " ";
    public static final String FLAG_MARK = "x";
    public static final String QUESTION_MARK = "?";

    private String player;

    private String id;

    private int rows;

    private int columns;

    private String[][] cellMines; // numbers from 0 to 8 or a '*' sign that represents a mine

    private String[][] cellState; // ' ' for uncovered cells, '.' for hidden cells, 'x' for mine marks, '?' for question marks*/

    public Game(String player, int rows, int columns) {
        if(rows < 0) {
            throw new InvalidGameSettingsException("Invalid amount of rows");
        }
        if(columns < 0) {
            throw new InvalidGameSettingsException("Invalid amount of columns");
        }
        this.player = player;
        this.rows = rows;
        this.columns = columns;
    }
    public Game(String player, String id, int rows, int columns) {
        this(player, rows, columns);
        this.id = id;
    }

    public void initialize(int mines) {
        if(mines > rows * columns) {
            throw new InvalidGameSettingsException("Invalid amount of mines");
        }

        cellMines = new String[rows][columns];
        cellState = new String[rows][columns];

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < columns; y++) {
                cellMines[x][y] = "0";
                cellState[x][y] = COVERED_CELL;
            }
        }
        Random random = new Random();
        for(int placedMines = 0; placedMines < mines; placedMines++) {
            int x = random.nextInt(rows);
            int y = random.nextInt(columns);
            while(cellMines[x][y].equals(MINE)) {
                x++;
                if(x == rows) {
                    x = 0;
                    y = (y + 1) % columns;
                }
            }
            cellMines[x][y] = MINE;
            for (int adjacentX = x - 1; adjacentX <= x + 1; adjacentX++) {
                for (int adjacentY = y - 1; adjacentY <= y + 1; adjacentY++) {
                    if (adjacentX >= 0 && adjacentX < rows && adjacentY >= 0 && adjacentY < columns) {
                        if (!cellMines[adjacentX][adjacentY].equals(MINE)) {
                            cellMines[adjacentX][adjacentY] = String.valueOf(Integer.parseInt(cellMines[adjacentX][adjacentY]) + 1);
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

    public String[][] getCellMines() {
        return cellMines;
    }

    public String[][] getCellState() {
        return cellState;
    }

    public void setCellMines(String[][] cellMines) {
        this.cellMines = cellMines;
    }

    public void setCellState(String[][] cellState) {
        this.cellState = cellState;
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
        if(cellMines[x][y].equals(MINE)) {
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    cellState[i][j] = UNCOVERED_CELL;
                }
            }
            return false;
        }
        else {
            if(cellMines[x][y].equals("0")) {
                for (int adjacentX = x - 1; adjacentX <= x + 1; adjacentX++) {
                    for (int adjacentY = y - 1; adjacentY <= y + 1; adjacentY++) {
                        if (adjacentX >= 0 && adjacentX < rows && adjacentY >= 0 && adjacentY < columns) {
                            if (!cellState[adjacentX][adjacentY].equals(UNCOVERED_CELL)) {
                                if(!uncoverCell(adjacentX, adjacentY)) {
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
        if (!cellState[x][y].equals(UNCOVERED_CELL)) {
            cellState[x][y] = FLAG_MARK;
        }
    }

    /**
     * Adds a question mark in the position (x, y)
     * @param x row
     * @param y column
     */
    public void addQuestionMark(int x, int y) {
        if (!cellState[x][y].equals(UNCOVERED_CELL)) {
            cellState[x][y] = QUESTION_MARK;
        }
    }

    public GameState checkState() {
        boolean coveredEmptyCell = false;
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(cellMines[i][j].equals(MINE) && cellState[i][j].equals(UNCOVERED_CELL)) {
                    return GameState.LOST;
                }
                if(!cellMines[i][j].equals(MINE) && !cellState[i][j].equals(UNCOVERED_CELL)) {
                    coveredEmptyCell = true;
                }
            }
        }
        if(coveredEmptyCell) {
            return GameState.ONGOING;
        }
        else {
            return GameState.WON;
        }
    }
}
