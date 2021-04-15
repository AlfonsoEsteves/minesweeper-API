package com.example.minesweeper.controller;

import com.example.minesweeper.controller.request.UserCreationRequest;
import com.example.minesweeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * This endpoint (as most other endpoints) will check if the user is authenticated
     * The user will be authenticated if:
     *  - He explicitly includes an Authentication header with basic credentials
     *  - If the browser already stored the user session.
     *
     * Use this endpoint to start the user session by explicitly including the Authentication header.
     * After calling this endpoint, the browser will remember the user session, and including the
     * Authentication header will not be necessary any more.
     */
    @GetMapping("/login")
    public void login() {
    }
}
