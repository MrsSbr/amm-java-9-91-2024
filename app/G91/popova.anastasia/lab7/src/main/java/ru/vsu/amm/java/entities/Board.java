package ru.vsu.amm.java.entities;

import java.util.UUID;

public class Board {

    private UUID boardID;
    private UUID userID;
    private String boardTitle;
    private String boardDescription;

    public Board() {};

    private UUID getBoardID() {
        return boardID;
    }

    private void setBoardID(UUID boardID) {
        this.boardID = boardID;
    }

    private UUID getUserID() {
        return userID;
    }

    private void setUserID(UUID userID) {
        this.userID = userID;
    }

    private String getBoardTitle() {
        return boardTitle;
    }

    private void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    private String getBoardDescription() {
        return boardDescription;
    }

    private void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }
}
