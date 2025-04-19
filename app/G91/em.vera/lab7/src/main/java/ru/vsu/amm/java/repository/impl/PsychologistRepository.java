package ru.vsu.amm.java.repository.impl;

import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entities.Psychologist;
import ru.vsu.amm.java.enums.Gender;
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

public class PsychologistRepository implements CrudRepository<Psychologist> {
    private final DataSource dataSource;

    public PsychologistRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public Optional<Psychologist> findById(Long id) throws SQLException {
        String sql = "SELECT id_psychologist, name, surname, birthdate, gender, experience, login, password " +
                "FROM psychologist WHERE id_psychologist = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Psychologist(
                            rs.getLong("id_psychologist"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getDate("birthdate").toLocalDate(),
                            Gender.getGenderByChar(rs.getString("gender").charAt(0)),
                            rs.getShort("experience"),
                            rs.getString("login"),
                            rs.getString("password")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Psychologist> findAll() throws SQLException {
        String sql = "SELECT id_psychologist, name, surname, birthdate, gender, experience, login, password FROM psychologist";
        List<Psychologist> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Psychologist(
                        rs.getLong("id_psychologist"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("birthdate").toLocalDate(),
                        Gender.getGenderByChar(rs.getString("gender").charAt(0)),
                        rs.getShort("experience"),
                        rs.getString("login"),
                        rs.getString("password")
                ));
            }
        }
        return list;
    }

    @Override
    public void save(Psychologist entity) throws SQLException {
        String sql = "INSERT INTO psychologist (name, surname, birthdate, gender, experience, login, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getSurname());
            ps.setDate(3, Date.valueOf(entity.getBirthdate()));
            ps.setString(4, entity.getGender().getCharacter().toString());
            ps.setShort(5, entity.getExperience());
            ps.setString(6, entity.getLogin());
            ps.setString(7, entity.getPassword());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Psychologist entity) throws SQLException {
        String sql = "UPDATE psychologist SET name = ?, surname = ?, birthdate = ?, gender = ?, " +
                "experience = ?, login = ?, password = ? WHERE id_psychologist = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getSurname());
            ps.setDate(3, Date.valueOf(entity.getBirthdate()));
            ps.setString(4, entity.getGender().getCharacter().toString());
            ps.setShort(5, entity.getExperience());
            ps.setString(6, entity.getLogin());
            ps.setString(7, entity.getPassword());
            ps.setLong(8, entity.getIdPsychologist());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM psychologist WHERE id_psychologist = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

}
