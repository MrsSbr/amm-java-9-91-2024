package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.exceptions.AddException;
import ru.vsu.amm.java.exceptions.DeleteException;
import ru.vsu.amm.java.exceptions.UpdateException;
import ru.vsu.amm.java.mapper.SessionMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.vsu.amm.java.utils.LoggerInitializer.initializeLogger;

public class SessionRepository implements ParkingRepository<Session> {

    private static final Logger logger = initializeLogger(
            "app/G91/simonov.ivan/lab7/src/main/java/ru/vsu/amm/java/logs/session-repository-logs.log",
            SessionRepository.class.getName());

    private final DataSource dataSource;

    public SessionRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Session> getById(int id) {

        String sql = """
                SELECT Id_session, Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate
                FROM "Session"
                WHERE Id_session = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            SessionMapper sessionMapper = new SessionMapper();

            if (rs.next()) {
                return Optional.of(sessionMapper.mapRowToObject(rs));
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return Optional.empty();

    }

    @Override
    public List<Session> getAll() {

        String sql = """
                SELECT Id_session, Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate
                FROM "Session"
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            List<Session> sessions = new ArrayList<>();

            SessionMapper sessionMapper = new SessionMapper();

            while (rs.next()) {
                sessions.add(sessionMapper.mapRowToObject(rs));
            }

            return sessions;

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return null;

    }

    @Override
    public int save(Session entity) {

        String sql = """
                INSERT INTO "Session" (Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection()) {

            SessionMapper sessionMapper = new SessionMapper();
            PreparedStatement stmt = sessionMapper.mapObjectToRow(entity, connection, sql);
            stmt.execute();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new AddException(e.getMessage());

        }

        return 0;
    }

    @Override
    public void update(Session entity) {

        String sql = """
                UPDATE "Session" SET Id_user = ?, Id_vehicle = ?, ParkingPrice = ?, EntryDate = ?, ExitDate = ?
                WHERE Id_session = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            SessionMapper sessionMapper = new SessionMapper();
            PreparedStatement stmt = sessionMapper.mapObjectToRow(entity, connection, sql);

            stmt.setInt(6, entity.getSessionId());
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new UpdateException(e.getMessage());

        }
    }

    @Override
    public void delete(int id) {

        String sql = """
                DELETE FROM "Session"
                WHERE Id_session = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DeleteException(e.getMessage());

        }
    }
}
