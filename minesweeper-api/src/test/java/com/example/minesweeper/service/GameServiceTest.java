package com.example.minesweeper.service;

import com.example.minesweeper.gamelogic.Game;
import com.example.minesweeper.repository.GameRepository;
import com.example.minesweeper.repository.SavedGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GameServiceTest {

    private static final String player = "testPlayer";
    private static final String gameId = "gameId";
    private static final int rows = 5;
    private static final int columns = 6;
    private static final int mines = 15;

    private static GameRepository gameRepositoryMock;

    @BeforeEach
    public void initialize() {
        Game game = new Game(player, gameId, rows, columns);
        game.initialize(0);
        SavedGame savedGame = new SavedGame(game);
        gameRepositoryMock = Mockito.mock(GameRepository.class);
        Mockito.when(gameRepositoryMock.save(Mockito.any())).thenReturn(savedGame);
        Mockito.when(gameRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(savedGame));
        Mockito.when(gameRepositoryMock.findByPlayer(player)).thenReturn(Collections.singletonList(savedGame));
    }

    @Test
    public void testStartNewGame() {
        GameService gameService = new GameService(gameRepositoryMock);
        String actualGameId = gameService.startNewGame(player, rows, columns, mines);
        Assertions.assertEquals(gameId, actualGameId);
    }

    @Test
    public void testPerformGameAction() {
        GameService gameService = new GameService(gameRepositoryMock);
        Game game = gameService.performGameAction(player, gameId, "flagMark", 0, 0);
        Assertions.assertEquals(Game.FLAG_MARK, game.getCellState()[0][0]);
        Mockito.verify(gameRepositoryMock).save(Mockito.any());
    }

    @Test
    public void testGetPlayerGameList() {
        GameService gameService = new GameService(gameRepositoryMock);
        List<String> games = gameService.getPlayerGameList(player);
        Assertions.assertEquals(1, games.size());
        Assertions.assertEquals(gameId, games.get(0));
    }

}
