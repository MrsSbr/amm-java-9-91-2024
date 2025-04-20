package ru.vsu.amm.java.repositories.impl;

import ru.vsu.amm.java.config.DatabaseConfiguration;
import ru.vsu.amm.java.entities.ClientEntity;
import ru.vsu.amm.java.repositories.CrudRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements CrudRepository<ClientEntity> {

    private final DataSource ds;

    public ClientRepository() {
        ds = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<ClientEntity> findById(Long id) throws SQLException {
        final String query = "SELECT client_id, name, email, password FROM client WHERE client_id = ?";
        try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(createClient(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ClientEntity> findAll() throws SQLException {
        final String query = "SELECT client_id, name, email, password FROM client";
        try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<ClientEntity> clients = new ArrayList<>();
            while (rs.next()) {
                clients.add(createClient(rs));
            }
            return clients;
        }
    }

    @Override
    public void save(ClientEntity entity) throws SQLException {
        final String query = "INSERT INTO client (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPassword());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(ClientEntity entity) throws SQLException {
        final String query = "UPDATE client SET name = ?, email = ?, password = ? WHERE client_id = ?";
        try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPassword());
            ps.setLong(4, entity.getClientId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM client WHERE client_id = ?";
        try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private ClientEntity createClient(ResultSet rs) throws SQLException {
        return new ClientEntity(
            rs.getLong("client_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password")
        );
    }
}
