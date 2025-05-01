package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.PurchaseHistory;
import ru.vsu.amm.java.repositories.PurchaseHistoryRepository;
import ru.vsu.amm.java.requests.CreatePurchaseHistoryRequest;
import ru.vsu.amm.java.responces.PurchaseHistoryResponce;
import ru.vsu.amm.java.service.interfaces.PurchaseHistoryService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    private static final Logger logger = Logger.getLogger(PurchaseHistoryServiceImpl.class.getName());
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public PurchaseHistoryServiceImpl() {
        purchaseHistoryRepository = new PurchaseHistoryRepository();
    }

    @Override
    public PurchaseHistory CreatePurchase(CreatePurchaseHistoryRequest request) throws SQLException {
        PurchaseHistory purchaseHistory = new PurchaseHistory(null,
                request.payment(),
                request.userId(),
                request.boardGameId()
        );
        purchaseHistoryRepository.save(purchaseHistory);
        logger.log(Level.INFO, "User " + purchaseHistory.getUserId()
                + " bought the game " + purchaseHistory.getBoardGameId());
        return purchaseHistory;
    }

    @Override
    public List<PurchaseHistoryResponce> getUserPurchases(Long userId) throws SQLException {
        return purchaseHistoryRepository.findByUserIdWithGames(userId);
    }
}
