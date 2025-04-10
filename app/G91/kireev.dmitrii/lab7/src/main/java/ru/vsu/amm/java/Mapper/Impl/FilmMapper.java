package ru.vsu.amm.java.Mapper.Impl;

import ru.vsu.amm.java.Mapper.Interface.Mapper;
import ru.vsu.amm.java.Model.Entity.FilmEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class FilmMapper implements Mapper<FilmEntity> {

    @Override
    public FilmEntity resultSetToEntity(ResultSet rs) throws SQLException {
        return new FilmEntity(rs.getLong("filmId"),
                rs.getString("name"),
                rs.getString("genre"),
                Duration.parse(rs.getString("duration")),
                rs.getString("screenWriter"),
                rs.getDouble("rating"));


    }
}

