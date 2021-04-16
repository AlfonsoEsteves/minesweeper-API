package com.example.minesweeper.service;

import com.example.minesweeper.gamelogic.Game;
import com.example.minesweeper.repository.GameRepository;
import com.example.minesweeper.repository.SavedGame;
import com.example.minesweeper.repository.SavedGameId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Start a new game and persist it in the database
     *
     * @param playerName name of the player
     * @param rows rows of the game board
     * @param columns columns of the game board
     * @param mines amount of mines in the game board
     * @return the new game id
     */
    public String startNewGame(String playerName, int rows, int columns, int mines) {
        Game game = new Game(playerName, rows, columns);
        game.initialize(mines);
        SavedGame savedGame = new SavedGame(game);
        return gameRepository.save(savedGame).getId();
    }

    /**
     * Get the list of all the games of the player
     *
     * @param playerName the name of the player
     * @return a list with the player game ids
     */
    public List<String> getPlayerGameList(String playerName) {
        List<String> playerGameIds = new ArrayList<>();
        Iterable<SavedGame> playerGames = gameRepository.findByPlayer(playerName);
        playerGames.forEach(game -> playerGameIds.add(game.getId()));
        return playerGameIds;
    }

    /**
     * Get a specific game
     * @param playerName the name of the player
     * @param gameId the id of the game
     * @return the requested game
     */
    public Game getGame(String playerName, String gameId) {
        return gameRepository.findById(new SavedGameId(playerName, gameId)).get().toGame();
    }

    /**
     * Perform an action over over the game on a specific cell
     * Actions can be either uncovering, marking with flag, and marking with question mark
     *
     * @param playerName the name of the player
     * @param gameId the id of the game where the action will be performed
     * @param action action to be performed
     * @param row row on which the action will be performed
     * @param column column on which the action will be performed
     * @return the state of the game after the action was performed
     */
    public Game performGameAction(String playerName, String gameId, String action, int row, int column) {
        Game game = gameRepository.findById(new SavedGameId(playerName, gameId)).get().toGame();
        switch (action) {
            case "uncover":
                game.uncoverCell(row, column);
                break;
            case "flagMark":
                game.addFlagMark(row, column);
                break;
            case "questionMark":
                game.addQuestionMark(row, column);
                break;
        }
        gameRepository.save(new SavedGame(game));
        return game;
    }
}
