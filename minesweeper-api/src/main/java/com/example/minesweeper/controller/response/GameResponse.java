package com.example.minesweeper.controller.response;

import com.example.minesweeper.model.Game;

/**
 * This is the game state that the player will be capable of seeing
 * Note that the player can not see the amount of mines of a cell that has not been uncovered yet
 */
public class GameResponse {

    public int rows;
    public int columns;
    public String[][] cells; // numbers from 0 to 8 or a '*' sign that represents a mine

    public GameResponse(Game game) {
        rows = game.getRows();
        columns = game.getColumns();
        cells = new String[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(game.getCellState()[i][j].equals(Game.UNCOVERED_CELL)) {
                    cells[i][j] = game.getCellMines()[i][j];
                }
                else {
                    cells[i][j] = game.getCellState()[i][j];
                }
            }
        }
    }
}
