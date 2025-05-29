package ru.vsu.amm.java.entities;

import java.util.UUID;

public class Column {

    private UUID columnID;
    private UUID boardID;
    private String columnTitle;

    public Column() {
        this.columnID = UUID.randomUUID();
    };

   public UUID getColumnID() {
        return columnID;
    }

    public void setColumnID(UUID columnID) {
        this.columnID = columnID;
    }

    public UUID getBoardID() {
        return boardID;
    }

    public void setBoardID(UUID boardID) {
        this.boardID = boardID;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }
}
