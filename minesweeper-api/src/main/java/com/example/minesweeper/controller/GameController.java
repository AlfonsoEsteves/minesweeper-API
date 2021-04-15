package com.example.minesweeper.controller;

import com.example.minesweeper.controller.request.NewGameRequest;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.repository.GameRepository;
import com.example.minesweeper.repository.SavedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Creates a new game session for the user.
     * The game sessions is persisted in the database.
     *
     * @param newGameRequest object containing the amount of mines and the grid dimentions
     * @return the state and id of the created game
     */
    @PostMapping("/game")
    public String startNewGame(@RequestBody NewGameRequest newGameRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Game game = new Game(username, newGameRequest.rows, newGameRequest.columns, newGameRequest.mines);
        SavedGame savedGame = new SavedGame(game);
        return gameRepository.save(savedGame).getId();
    }

    /**
     * Returns a list with the id of the player's games
     */
    @GetMapping("/game")
    public List<String> gameList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> playerGameIds = new ArrayList<>();
        Iterable<SavedGame> playerGames = gameRepository.findByPlayer(username);
        playerGames.forEach(game -> playerGameIds.add(game.getId()));
        return playerGameIds;
    }

}
