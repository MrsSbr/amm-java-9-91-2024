package ru.vsu.amm.java.service;

public interface AuthService {
    boolean login(String login, String password);

    boolean register(String login, String password);
}
