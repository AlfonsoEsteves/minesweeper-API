package com.example.minesweeper.service.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super("The name has already been taken by another user");
    }
}
