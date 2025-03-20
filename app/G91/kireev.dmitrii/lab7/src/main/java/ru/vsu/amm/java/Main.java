package ru.vsu.amm.java;

import ru.vsu.amm.java.DI.Autowired;
import ru.vsu.amm.java.DI.MainComponent;

import java.sql.SQLException;

@MainComponent
public class Main {


    @Autowired
    private UserService userService;

    public void run() throws SQLException {
        userService.userRepo.delete(1L);
        System.out.println("Application started!");
    }
}
