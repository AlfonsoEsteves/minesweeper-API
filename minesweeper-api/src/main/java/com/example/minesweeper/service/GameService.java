package com.example.minesweeper.service;

import com.example.minesweeper.model.Game;
import com.example.minesweeper.repository.GameRepository;
import com.example.minesweeper.repository.SavedGame;
import com.example.minesweeper.repository.SavedGameId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public String startNewGame(String username, int rows, int columns, int mines) {
        Game game = new Game(username, rows, columns);
        game.initialize(mines);
        SavedGame savedGame = new SavedGame(game);
        return gameRepository.save(savedGame).getId();
    }

    public List<String> getPlayerGameList(String username) {
        List<String> playerGameIds = new ArrayList<>();
        Iterable<SavedGame> playerGames = gameRepository.findByPlayer(username);
        playerGames.forEach(game -> playerGameIds.add(game.getId()));
        return playerGameIds;
    }

    public Game getGame(String username, String gameId) {
        SavedGame savedGame = gameRepository.findById(new SavedGameId(username, gameId)).get();
        return savedGame.toGame();
    }

    public Game performGameAction(String username, String gameId, String action, int row, int column) {
        return gameRepository.findById(new SavedGameId(username, gameId)).get().toGame();
    }
}
