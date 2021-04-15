package com.example.minesweeper.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.minesweeper.model.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "MineSweeperGame")
public class SavedGame {

    @Id
    private SavedGameId savedGameId;

    private String player;

    private String id;

    private int rows;

    private int columns;

    private String cellMines;

    private String cellState;

    public SavedGame() {
    }

    public SavedGame(Game game) {
        player = game.getPlayer();
        id = game.getId();
        savedGameId = new SavedGameId(player, id);
        rows = game.getRows();
        columns = game.getColumns();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            cellMines = objectMapper.writeValueAsString(game.getCellMines());
            cellState = objectMapper.writeValueAsString(game.getCellState());
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @DynamoDBHashKey(attributeName = "player")
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @DynamoDBRangeKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "rows")
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @DynamoDBAttribute(attributeName = "columns")
    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @DynamoDBAttribute(attributeName = "cellMines")
    public String getCellMines() {
        return cellMines;
    }

    public void setCellMines(String cellMines) {
        this.cellMines = cellMines;
    }

    @DynamoDBAttribute(attributeName = "cellState")
    public String getCellState() {
        return cellState;
    }

    public void setCellState(String cellState) {
        this.cellState = cellState;
    }

    public Game toGame() {
        Game game = new Game(player, rows, columns);
        ObjectMapper objectMapper = new ObjectMapper();




        game.setCellMines(objectMapper.convertValue(cellMines, String[][].class));
        game.setCellState(objectMapper.convertValue(cellState, String[][].class));
        return game;
    }
}
