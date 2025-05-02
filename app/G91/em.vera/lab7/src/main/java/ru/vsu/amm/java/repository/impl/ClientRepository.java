package ru.vsu.amm.java.repository.impl;

import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.repository.CrudRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements CrudRepository<Client> {
    private final DataSource dataSource;

    public ClientRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public Optional<Client> findById(Long id) throws SQLException {
        String sql = "SELECT id_client, email, name, password FROM client WHERE id_client = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Client(
                            rs.getLong("id_client"),
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getString("password")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Client> findAll() throws SQLException {
        String sql = "SELECT id_client, email, name, password FROM client";
        List<Client> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Client(
                        rs.getLong("id_client"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("password")
                ));
            }
        }
        return list;
    }

    @Override
    public void save(Client client) throws SQLException {
        String sql = "INSERT INTO client (email, name, password) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, client.getEmail());
            ps.setString(2, client.getName());
            ps.setString(3, client.getPassword());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Client client) throws SQLException {
        String sql = "UPDATE client SET email = ?, name = ?, password = ? WHERE id_client = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getEmail());
            ps.setString(2, client.getName());
            ps.setString(3, client.getPassword());
            ps.setLong(4, client.getIdClient());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM client WHERE id_client = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public Optional<Client> findByEmail(String email) throws SQLException {
        String sql = "SELECT id_client, email, name, password FROM client WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Client(
                            rs.getLong("id_client"),
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getString("password")
                    ));
                }
                return Optional.empty();
            }
        }
    }
}