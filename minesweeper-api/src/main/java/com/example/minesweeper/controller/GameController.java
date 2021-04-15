package com.example.minesweeper.controller;

import com.example.minesweeper.controller.request.GameActionRequest;
import com.example.minesweeper.controller.request.NewGameRequest;
import com.example.minesweeper.controller.response.GameResponse;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

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
        return gameService.startNewGame(username, newGameRequest.rows, newGameRequest.columns, newGameRequest.mines);
    }

    /**
     * Returns a list with the id of the player's games
     */
    @GetMapping("/game")
    public List<String> gamePlayerGameList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return gameService.getPlayerGameList(username);
    }

    @GetMapping("/game/{gameId}")
    public GameResponse getGame(@PathVariable String gameId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Game game = gameService.getGame(username, gameId);
        return new GameResponse(game);
    }

    @PostMapping("/game/{gameId}")
    public GameResponse gameAction(@PathVariable String gameId, @RequestBody GameActionRequest gameActionRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Game game = gameService.performGameAction(
                username,
                gameId,
                gameActionRequest.action,
                gameActionRequest.row,
                gameActionRequest.column);
        return new GameResponse(game);
    }

}
