package ru.vsu.amm.java.entities;

import java.util.UUID;

public class Column {

    private UUID columnID;
    private UUID boardID;
    //private UUID userID; —— не уверена, что нужно сюда тащить этот айдишник
    private String columnTitle;

    public Column() {};

    private UUID getColumnID() {
        return columnID;
    }

    private void setColumnID(UUID columnID) {
        this.columnID = columnID;
    }

    private UUID getBoardID() {
        return boardID;
    }

    private void setBoardID(UUID boardID) {
        this.boardID = boardID;
    }

    private String getColumnTitle() {
        return columnTitle;
    }

    private void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }
}
