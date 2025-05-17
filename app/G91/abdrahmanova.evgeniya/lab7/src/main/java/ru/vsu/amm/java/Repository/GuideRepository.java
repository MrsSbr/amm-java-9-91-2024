package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.Guide;
import ru.vsu.amm.java.Enums.Language;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuideRepository implements Repository<Guide> {

    private final Logger logger = Logger.getLogger(GuideRepository.class.getName());
    private final DataSource dataSource;

    public GuideRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<Guide> findById(int id) throws SQLException {

        final String sql = "SELECT Full_name, Bio, Rating, Languages, E_mail, Number_phone" +
                " FROM Guide WHERE id = ?";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                Language language = Language.valueOf(rs.getString("Languages"));
                return Optional.of(new Guide(
                        rs.getInt("id_guide"),
                        rs.getString("Full_name"),
                        rs.getString("Bio"),
                        rs.getDouble("Rating"),
                        language,
                        rs.getString("E_mail"),
                        rs.getString("Number_phone")
                ));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Guide> findAll() throws SQLException {
        List<Guide> guides = new ArrayList<>();
        final String sql = "SELECT * FROM guide";
        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                Language language = Language.valueOf(rs.getString("Languages"));
                guides.add(new Guide(
                        rs.getInt("id_guide"),
                        rs.getString("Full_name"),
                        rs.getString("Bio"),
                        rs.getDouble("Rating"),
                        language,
                        rs.getString("E_mail"),
                        rs.getString("Number_phone")
                ));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
        return List.of();
    }

    @Override
    public void update(Guide entity) throws SQLException {
        final String sql = "UPDATE Guide SET Full_name = ?, Bio = ?," +
                "Rating = ?, Languages = ?, E_mail = ?, Number_phone = ? WHERE id_guide = ?";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getBio());
            ps.setDouble(3, entity.getRating());
            ps.setString(4, String.valueOf(entity.getLanguages()));
            ps.setString(5, entity.getEMail());
            ps.setString(6, entity.getNumberPhone());
            ps.setInt(7, entity.getId());

            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void save(Guide entity) throws SQLException {
        final String sql = "INSERT INTO Guide(Full_name, Bio, Rating, Languages, E_mail, Number_phone) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getBio());
            ps.setDouble(3, entity.getRating());
            ps.setString(4, String.valueOf(entity.getLanguages()));
            ps.setString(5, entity.getEMail());
            ps.setString(6, entity.getNumberPhone());

            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void delete(Guide entity) throws SQLException {
        final String sql = "DELETE FROM Guide WHERE id_guide = ?";
        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }
}
