package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.User;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements Repository<User> {

    private final Logger logger = Logger.getLogger(UserRepository.class.getName());
    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        final String sql = "SELECT id_user, Full_name, Birthday, E_mail, Number_phone" +
                " FROM User_tour WHERE id = ?";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id_user"),
                        rs.getString("Full_name"),
                        rs.getDate("Birthday").toLocalDate(),
                        rs.getString("E_mail"),
                        rs.getString("Number_phone")
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
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();

        final String sql = "SELECT id_user, Full_name, Birthday, E_mail, Number_phone" +
                " FROM User_tour";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id_user"),
                        rs.getString("Full_name"),
                        rs.getDate("Birthday").toLocalDate(),
                        rs.getString("E_mail"),
                        rs.getString("Number_phone")
                ));
            }
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
        return List.of();
    }

    @Override
    public void update(User entity) throws SQLException {
        final String sql = "UPDATE User_tour SET Full_name = ?, Birthday = ?," +
                "E_mail = ?, Number_phone WHERE id_user = ?";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, entity.getFullName());
            ps.setDate(2, Date.valueOf(entity.getBirthday()));
            ps.setString(3, entity.geteMail());
            ps.setString(4, entity.getNumberPhone());
            ps.setInt(5, entity.getId());

            ps.execute();
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void save(User entity) throws SQLException {
        final String sql = "INSERT INTO User_tour(Full_name, Birthday, E_mail, Number_phone)" +
                " VALUES (?,?,?,?)";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, entity.getFullName());
            ps.setDate(2, Date.valueOf(entity.getBirthday()));
            ps.setString(3, entity.geteMail());
            ps.setString(4, entity.getNumberPhone());

            ps.execute();
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void delete(User entity) throws SQLException {
        final String sql = "DELETE FROM User WHERE id_user = ?";
        try (var conn = dataSource.getConnection()) {
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
