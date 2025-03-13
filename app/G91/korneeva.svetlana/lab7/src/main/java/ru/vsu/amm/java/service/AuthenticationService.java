package ru.vsu.amm.java.service;

public interface AuthenticationService {

    void login(String email, String hashPassword);

    void register(String email, String hashPassword);
}
