package com.example.minesweeper.repository.exception;

public class GameLoadingException extends RuntimeException{
    public GameLoadingException(String message) {
        super(message);
    }
}
