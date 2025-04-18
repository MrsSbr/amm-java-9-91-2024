package ru.vsu.amm.java.requests;

public record CreatePurchaseHistoryRequest(int payment, Long userId, Long boardGameId) {
}
