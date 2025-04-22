package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.repository.BoardRepository;

import java.util.List;
import java.util.UUID;

public class BoardService implements BoardServiceInterface{

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Override
    public Board createBoard(UUID userID, String title, String description){
        Board board = new Board();
        board.setUserID(userID);
        board.setBoardTitle(title);
        board.setBoardDescription(description);

        boardRepository.save(board);
        return board;
    }

    @Override
    public Board getBoardByID(UUID boardID){
        return boardRepository.getByID(boardID);
    }

    @Override
    public List<Board> getAllBoards(){
        return boardRepository.getAll();
    }

    @Override
    public Board updateBoardTitle(UUID boardID, String newTitle){
        Board board = boardRepository.getByID(boardID);

        if (board != null) {
            if (newTitle != null) {
                board.setBoardTitle(newTitle);
            }
            boardRepository.update(board);
        }

        return board;
    }

    @Override
    public Board updateBoardDescription(UUID boardID, String newDescription){
        Board board = boardRepository.getByID(boardID);

        if (board != null){
            if (newDescription != null){
                board.setBoardDescription(newDescription);
            }
            boardRepository.update(board);
        }

        return board;
    }

    @Override
    public boolean deleteBoard(UUID boardID){
        Board board = boardRepository.getByID(boardID);

        if (board != null){
           boardRepository.delete(boardID);
           return true;
        }

        return false;
    }

}
