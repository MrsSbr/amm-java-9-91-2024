package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.UserEntity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Hello World!");
        Connection con = DatabaseAccess.getDataSource().getConnection();
        System.out.println(con.getCatalog());
        final String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";

        try (PreparedStatement statement = con.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            System.out.println("Tables in public schema:");

            while (rs.next()) {
                String tableName = rs.getString("table_name");
                System.out.println(" - " + tableName);
            }
        }

        con.close();
    }
}