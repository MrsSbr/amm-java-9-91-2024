package ru.vsu.amm.java.Exception;

import java.sql.SQLException;

public class DbException extends RuntimeException  {
    public DbException(String message, SQLException e) {
        super(message);
    }
}
