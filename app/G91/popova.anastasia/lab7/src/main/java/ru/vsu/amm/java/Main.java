package ru.vsu.amm.java;

import org.apache.catalina.LifecycleException;
import ru.vsu.amm.java.DB_config.DatabaseConnection;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {



        try (Connection c = DatabaseConnection.getConnection()){
            System.out.println("Connected successfully! HikariCP is working");
        } catch (SQLException e){
            System.err.println("Connection failed! " + e.getMessage());
        } finally{
            DatabaseConnection.shutdown();
        }





    }
}