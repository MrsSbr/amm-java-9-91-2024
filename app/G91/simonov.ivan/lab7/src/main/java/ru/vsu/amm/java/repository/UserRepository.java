package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.mappers.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements ParkingRepository<User> {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<User> getById(int id) throws SQLException {

        String sql = "SELECT Id_user, LastName, FirstName, Patronymic, Login, Password, Role FROM User WHERE Id_user = ?";

        Connection connection = dataSource.getConnection();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return Optional.of(UserMapper.mapRowToObject(rs));
        }

        return Optional.empty();
    }

    @Override
    public List<User> getAll() throws SQLException {

        String sql = "SELECT Id_user, LastName, FirstName, Patronymic, Login, Password, Role FROM User";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<User> users = new ArrayList<>();

        while (rs.next()) {
            users.add(UserMapper.mapRowToObject(rs));
        }

        return users;
    }

    @Override
    public void save(User entity) throws SQLException {

        String sql = "INSERT INTO User (LastName, FirstName, Patronymic, Login, Password, Role) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        UserMapper.mapObjectToRow(entity, stmt);
        stmt.execute();
    }
}
