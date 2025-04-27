package ru.vsu.amm.java.repository.impl;

import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.repository.CrudRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionRepository implements CrudRepository<Session> {
    private final DataSource dataSource;

    public SessionRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public Optional<Session> findById(Long id) throws SQLException {
        String sql =
                "SELECT id_session, id_psychologist, id_client, date, price, duration " +
                        "FROM session " +
                        "WHERE id_session = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Session(
                            rs.getLong("id_session"),
                            rs.getLong("id_psychologist"),
                            rs.getLong("id_client"),
                            rs.getDate("date").toLocalDate(),
                            rs.getBigDecimal("price"),
                            rs.getShort("duration")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Session> findAll() throws SQLException {
        String sql =
                "SELECT id_session, id_psychologist, id_client, date, price, duration FROM session";
        List<Session> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Session(
                        rs.getLong("id_session"),
                        rs.getLong("id_psychologist"),
                        rs.getLong("id_client"),
                        rs.getDate("date").toLocalDate(),
                        rs.getBigDecimal("price"),
                        rs.getShort("duration")
                ));
            }
        }
        return list;
    }

    public Optional<Session> findByClient(Long clientId) throws SQLException {
        String sql =
                "SELECT id_session, id_psychologist, id_client, date, price, duration " +
                        "FROM session " +
                        "WHERE id_client = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Session(
                            rs.getLong("id_session"),
                            rs.getLong("id_psychologist"),
                            rs.getLong("id_client"),
                            rs.getDate("date").toLocalDate(),
                            rs.getBigDecimal("price"),
                            rs.getShort("duration")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<Session> findByPsychologist(Long psychologistId) throws SQLException {
        String sql =
                "SELECT id_session, id_psychologist, id_client, date, price, duration " +
                        "FROM session " +
                        "WHERE id_psychologist = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, psychologistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Session(
                            rs.getLong("id_session"),
                            rs.getLong("id_psychologist"),
                            rs.getLong("id_client"),
                            rs.getDate("date").toLocalDate(),
                            rs.getBigDecimal("price"),
                            rs.getShort("duration")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void save(Session entity) throws SQLException {
        String sql =
                "INSERT INTO session (id_psychologist, id_client, date, price, duration) " +
                        "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, entity.getId_psychologist());
            ps.setLong(2, entity.getId_client());
            ps.setDate(3, Date.valueOf(entity.getDate()));
            ps.setBigDecimal(4, entity.getPrice());
            ps.setShort(5, entity.getDuration());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Session entity) throws SQLException {
        String sql =
                "UPDATE session SET id_psychologist = ?, id_client = ?, date = ?, price = ?, duration = ? " +
                        "WHERE id_session = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, entity.getId_psychologist());
            ps.setLong(2, entity.getId_client());
            ps.setDate(3, Date.valueOf(entity.getDate()));
            ps.setBigDecimal(4, entity.getPrice());
            ps.setShort(5, entity.getDuration());
            ps.setLong(6, entity.getIdSession());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM session WHERE id_session = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
