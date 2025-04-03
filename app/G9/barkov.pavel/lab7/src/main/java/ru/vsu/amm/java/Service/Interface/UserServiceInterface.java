package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Service.Entities.ShareholderCreateModel;

import java.sql.SQLException;

public interface UserServiceInterface {
    boolean register(ShareholderCreateModel user,String password) throws SQLException;
    boolean login(String email,String password) throws SQLException;
}
