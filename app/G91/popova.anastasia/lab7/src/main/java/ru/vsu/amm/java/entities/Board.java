package ru.vsu.amm.java.entities;

import java.util.UUID;

public class Board {

    private UUID boardID;
    private UUID userID;
    private String boardTitle;
    private String boardDescription;

    public Board() {
        this.boardID = UUID.randomUUID();
    };

    public UUID getBoardID() {
        return boardID;
    }

    public void setBoardID(UUID boardID) {
        this.boardID = boardID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardDescription() {
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }
}
