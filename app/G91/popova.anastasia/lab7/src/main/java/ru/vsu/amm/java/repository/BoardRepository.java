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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BoardRepository implements CRUDRepository<Board> {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BoardRepository.class);

    private Board mapResultSetToBoard(ResultSet rs) throws SQLException{
        Board board = new Board();
        board.setBoardID((UUID)rs.getObject("BoardID"));
        board.setUserID((UUID)rs.getObject("UserID"));
        board.setBoardTitle(rs.getString("BoardTitle"));
        board.setBoardDescription(rs.getString("BoardDescription"));
        return board;
    }

    @Override
    public Board getByID(UUID boardID) {
        final String sql = "SELECT BoardID, UserID, BoardTitle, BoardDescription FROM boards WHERE BoardID = ?";

        log.debug("getting board by ID^ {}", boardID);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, boardID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Board board = mapResultSetToBoard(rs);
                log.debug("found board: {}", board);
                return board;
            }

        } catch (SQLException e) {
            log.error("failed to get board by ID: {}", boardID, e);
        }

        return null;
    }

    @Override
    public List<Board> getAll() {
        List<Board> boards = new ArrayList<>();
        final String sql = "SELECT BoardID, UserID, BoardTitle, BoardDescription FROM boards";
        log.debug("getting all boards...");

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                boards.add(mapResultSetToBoard(rs));
            }
            log.debug("found {} boards", boards.size());

        } catch (SQLException e) {
            log.error("error while trying to get all boards: ", e);
            //return null;
        }

        return boards;
    }

    @Override
    public void save(Board board) {
        final String sql = "INSERT INTO boards (BoardID, UserID, BoardTitle, BoardDescription) VALUES (?, ?, ?, ?)";
        log.debug("saving new board: {}", board);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, board.getBoardID());
            ps.setObject(2, board.getUserID());
            ps.setString(3, board.getBoardTitle());
            ps.setString(4, board.getBoardDescription());
            ps.executeUpdate();

            log.info("board saved successfully: {}", board.getBoardID());

        } catch (SQLException e) {
            log.error("error while trying to save board: {}", board, e);
        }

    }

    @Override
    public void update(Board board) {
        final String sql = "UPDATE boards SET BoardTitle = ?, BoardDescription = ? WHERE BoardID = ?";
        log.debug("updating board: {}", board);

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

            log.info("board updated successfully: {}", board.getBoardID());

        } catch (SQLException e) {
           log.error("error while trying to update board: {}", board, e);
        }

    }

    @Override
    public void delete(UUID boardID) {
        final String sql = "DELETE FROM boards WHERE BoardID = ?";
        log.debug("deleting board: {}", boardID);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

             ps.setObject(1, boardID);
             ps.executeUpdate();

            log.info("board deleted successfully: {}", boardID);


        } catch (SQLException e) {
            log.error("error while trying to delete board: {}", boardID, e);
        }

    }

}
