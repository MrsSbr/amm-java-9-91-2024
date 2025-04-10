package ru.vsu.amm.java.Mapper.Impl;

import ru.vsu.amm.java.Mapper.Interface.Mapper;
import ru.vsu.amm.java.Model.Entity.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<UserEntity> {

    @Override
    public UserEntity resultSetToEntity(ResultSet rs) throws SQLException {
        return new UserEntity(rs.getLong("userId"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone"));
    }
}
