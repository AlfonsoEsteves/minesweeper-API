package com.example.minesweeper.controller;

import com.example.minesweeper.controller.request.UserCreationRequest;
import com.example.minesweeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user and persists it in the database.
     *
     * @param userCreationRequest object containing the user name and password
     */
    @PostMapping("/user")
    public void createUser(@RequestBody UserCreationRequest userCreationRequest) {
        userService.createUser(userCreationRequest.name, userCreationRequest.password);
    }
}
