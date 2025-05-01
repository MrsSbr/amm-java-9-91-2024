package ru.vsu.amm.java.responces;

import ru.vsu.amm.java.entities.BoardGame;

public record PurchaseHistoryResponce(Long orderNumber, int payment, BoardGame boardGame) {
}
