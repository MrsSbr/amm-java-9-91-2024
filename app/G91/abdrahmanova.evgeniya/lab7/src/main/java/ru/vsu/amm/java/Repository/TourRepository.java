package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.Tour;
import ru.vsu.amm.java.Enums.Language;

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

public class TourRepository implements Repository<Tour> {

    private final Logger logger = Logger.getLogger(TourRepository.class.getName());
    private final DataSource dataSource;

    public TourRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<Tour> findById(int id) throws SQLException {
        final String sql = "SELECT id_tour, id_guide, Title, Description, Duration," +
                "Price, Max_participants, Start_location, Language_tour FROM Tour WHERE id_tour = ?";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                Language language = Language.valueOf(rs.getString("Language_tour"));
                return Optional.of(new Tour(
                        rs.getInt("id_tour"),
                        rs.getInt("id_guide"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("Duration"),
                        rs.getInt("Price"),
                        rs.getInt("Max_participants"),
                        rs.getString("Start_location"),
                        language
                ));
            }
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Tour> findAll() throws SQLException {
        List<Tour> tours = new ArrayList<>();
        final String sql = "SELECT id_tour, id_guide, Title, Description, Duration, Price, " +
                "Max_participants, Start_location, Language_tour FROM Tour";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                Language language = Language.valueOf(rs.getString("Language_tour"));
                tours.add(new Tour(
                        rs.getInt("id_tour"),
                        rs.getInt("id_guide"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("Duration"),
                        rs.getInt("Price"),
                        rs.getInt("Max_participants"),
                        rs.getString("Start_location"),
                        language
                ));
            }
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
        return tours;
    }

    @Override
    public void update(Tour entity) throws SQLException {
        final String sql = "UPDATE Tour SET id_guide = ?, Title = ?, Description = ?, Duration = ?," +
                "Price = ?, Max_participants = ?, Start_location = ?, Language_tour = ? WHERE id_tour = ?";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, entity.getIdGuide());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getDescription());
            ps.setDouble(4, entity.getDuration());
            ps.setInt(5, entity.getMaxParticipants());
            ps.setString(6, entity.getStartLocation());
            ps.setString(7, String.valueOf(entity.getLanguages()));
            ps.setInt(8, entity.getId());

            ps.execute();
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void save(Tour entity) throws SQLException {
        final String sql = "INSERT INTO Tour(id_guide, Title, Description, Duration, Price, Max_participants," +
                "Start_location, Language_tour) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, entity.getIdGuide());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getDescription());
            ps.setInt(4, entity.getPrice());
            ps.setInt(5, entity.getMaxParticipants());
            ps.setString(6, entity.getStartLocation());
            ps.setString(7, String.valueOf(entity.getLanguages()));

            ps.execute();
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void delete(Tour entity) throws SQLException {
        final String sql = "DELETE FROM Tour WHERE id_tour = ?";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.execute();
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }
}
