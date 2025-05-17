package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Service.Entities.ShareholderCreateModel;

import java.sql.SQLException;
import java.util.Optional;

public interface UserServiceInterface {
    boolean register(ShareholderCreateModel user, String password) throws SQLException;

    Shareholder login(String email, String password) throws SQLException;

    Optional<Shareholder> get(int userId) throws SQLException;
}
