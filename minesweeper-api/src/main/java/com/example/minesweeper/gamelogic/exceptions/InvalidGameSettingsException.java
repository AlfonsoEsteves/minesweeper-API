package com.example.minesweeper.gamelogic.exceptions;

public class InvalidGameSettingsException extends RuntimeException{

    public InvalidGameSettingsException(String msg) {
        super(msg);
    }
}
