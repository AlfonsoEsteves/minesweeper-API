package com.example.minesweeper.gamelogic;

import com.example.minesweeper.gamelogic.exceptions.InvalidGameSettingsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    private final String player = "testPlayer";
    private final int rows = 5;
    private final int columns = 6;
    private final int mines = 15;

    @Test
    public void testInitialize_correctMineCount() {
        Game game = new Game(player, rows, columns);
        game.initialize(mines);

        int actualMinesNumber = 0;
        String[][] cellMines = game.getCellMines();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(cellMines[i][j].equals(Game.MINE)) {
                    actualMinesNumber++;
                }
            }
        }
        Assertions.assertEquals(mines, actualMinesNumber);
    }

    @Test
    public void testInitialize_tooManyMinesError() {
        Game game = new Game(player, rows, columns);
        Assertions.assertThrows(InvalidGameSettingsException.class, () -> {
            game.initialize(1000);
        });
    }

    @Test
    public void testUncoverCell_uncoveringEmptyBoardAndWinning() {
        Game game = new Game(player, rows, columns);
        game.initialize(0);
        boolean validAction = game.uncoverCell(0,0);
        GameState state = game.checkState();
        Assertions.assertTrue(validAction);
        Assertions.assertEquals(GameState.WON, state);
    }

    @Test
    public void testUncoverCell_uncoveringEmptyCellOfOngoingGame() {
        Game game = new Game(player, rows, columns);
        game.initialize(0);
        placeMineInTheTopLeftCorner(game);
        boolean validAction = game.uncoverCell(1,0);
        GameState state = game.checkState();
        Assertions.assertTrue(validAction);
        Assertions.assertEquals(GameState.ONGOING, state);
    }

    @Test
    public void testUncoverCell_uncoveringMineAndLosing() {
        Game game = new Game(player, rows, columns);
        game.initialize(0);
        placeMineInTheTopLeftCorner(game);
        boolean validAction = game.uncoverCell(0,0);
        GameState state = game.checkState();
        Assertions.assertFalse(validAction);
        Assertions.assertEquals(GameState.LOST, state);
    }

    private void placeMineInTheTopLeftCorner(Game game) {
        game.getCellMines()[0][0] = Game.MINE;
        game.getCellMines()[0][1] = "1";
        game.getCellMines()[1][1] = "1";
        game.getCellMines()[1][0] = "1";
    }
}
