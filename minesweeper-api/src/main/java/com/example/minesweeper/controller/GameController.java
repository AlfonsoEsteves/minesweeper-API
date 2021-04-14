package com.example.minesweeper.controller;

import com.example.minesweeper.controller.request.NewGameRequest;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.model.User;
import com.example.minesweeper.repository.GameRepository;
import com.example.minesweeper.repository.SavedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public SavedGame startNewGame(@RequestBody NewGameRequest newGameRequest) {

        // TODO:
        // Until I implement the authentication, this endpoint will work with a hardcoded user
        User user = new User("alfonso", 0);

        Game game = new Game(user, newGameRequest.rows, newGameRequest.columns, newGameRequest.mines);
        SavedGame savedGame = new SavedGame(game);
        return gameRepository.save(savedGame);
    }
}
