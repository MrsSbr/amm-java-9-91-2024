package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.entities.PurchaseHistory;
import ru.vsu.amm.java.requests.CreatePurchaseHistoryRequest;
import ru.vsu.amm.java.responces.PurchaseHistoryResponce;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseHistoryService {
    PurchaseHistory CreatePurchase(CreatePurchaseHistoryRequest request) throws SQLException;

    List<PurchaseHistoryResponce> getUserPurchases(Long userId) throws SQLException;
}
