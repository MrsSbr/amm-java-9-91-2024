package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Board;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.logging.Logger;

public class BoardRepository implements CRUDRepository<Board> {
    private static final Logger logger = Logger.getLogger(BoardRepository.class.getName());

    @Override
    public Board getByID(UUID boardID) {
        final String sql = "SELECT BoardID, UserID, BoardTitle, BoardDescription FROM boards WHERE BoardID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, boardID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Board board = new Board();
                board.setBoardID((UUID)rs.getObject("BoardID"));
                board.setUserID((UUID)rs.getObject("UserID"));
                board.setBoardTitle(rs.getString("BoardTitle"));
                board.setBoardDescription(rs.getString("BoardDescription"));
                return board;
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get board: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Board> getAll() {
        List<Board> boards = new ArrayList<>();
        final String sql = "SELECT BoardID, UserID, BoardTitle, BoardDescription FROM boards";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Board board = new Board();
                board.setBoardID((UUID) rs.getObject("BoardID"));
                board.setUserID((UUID) rs.getObject("UserID"));
                board.setBoardTitle(rs.getString("BoardTitle"));
                board.setBoardDescription(rs.getString("BoardDescription"));
                boards.add(board);
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get all boards: " + e.getMessage());
            //return null;
        }

        return boards;
    }

    @Override
    public void save(Board board) {
        final String sql = "INSERT INTO boards (BoardID, UserID, BoardTitle, BoardDescription) VALUES (?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, board.getBoardID());
            ps.setObject(2, board.getUserID());
            ps.setString(3, board.getBoardTitle());
            ps.setString(4, board.getBoardDescription());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to save board: " + e.getMessage());
        }

    }

    @Override
    public void update(Board board) {
        final String sql = "UPDATE boards SET BoardTitle = ?, BoardDescription = ? WHERE BoardID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, board.getBoardTitle());
            if (board.getBoardDescription() != null) {
                ps.setString(2, board.getBoardDescription());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            ps.setObject(3, board.getBoardID());


            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to update board: " + e.getMessage());
        }

    }

    @Override
    public void delete(UUID boardID) {
        final String sql = "DELETE FROM boards WHERE BoardID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

             ps.setObject(1, boardID);
             ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to delete board: " + e.getMessage());
        }

    }

}
