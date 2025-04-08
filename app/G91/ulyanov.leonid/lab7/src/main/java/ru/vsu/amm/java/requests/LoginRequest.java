package ru.vsu.amm.java.requests;

import com.sun.net.httpserver.Request;

public record LoginRequest(String email, String password) {
}
