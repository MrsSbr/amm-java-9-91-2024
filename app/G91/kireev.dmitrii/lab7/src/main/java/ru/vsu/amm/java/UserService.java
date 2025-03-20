package ru.vsu.amm.java;

import ru.vsu.amm.java.DI.Autowired;
import ru.vsu.amm.java.Repository.UserRepo;

public class UserService {

    @Autowired
    public UserRepo userRepo;
}
