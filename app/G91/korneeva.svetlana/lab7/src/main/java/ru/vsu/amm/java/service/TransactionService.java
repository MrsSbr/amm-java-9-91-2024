package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.Transaction;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.entity.response.TransactionResponse;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.exception.TransactionException;
import ru.vsu.amm.java.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionResponse findAll(UserEntity user) {
        TransactionRepository transactionRepository = new TransactionRepository();

        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findByUserId(user.getId());
        } catch (SQLException e) {
            logger.error("������ ��� ��������� ���� ���������� ��� ������������ � id={}", user.getId(), e);
            throw new TransactionException("Unknown exception");
        }

        logger.info("������� {} ���������� ��� ������������ � id={}", transactions.size(), user.getId());
        return getTransactionResponse(transactions);
    }

    public TransactionResponse findByTimeRange(UserEntity user, LocalDateTime start, LocalDateTime end) {
        TransactionRepository transactionRepository = new TransactionRepository();

        if (start.isAfter(end)) {
            logger.info("��������� ���� ����� ��������: {} > {}", start, end);
            throw new TransactionException("��������� ���� ����� ��������");
        }

        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findByUserIdAndTimeRange(user.getId(), start, end);
            logger.info("������� {} ���������� � ��������� {} - {} ��� ������������ � id={}", transactions.size(), start, end, user.getId());
        } catch (SQLException e) {
            logger.error("������ ��� ��������� ���������� �� ��������� �������", e);
            throw new TransactionException("Unknown exception");
        }

        return getTransactionResponse(transactions);
    }

    public void save(UserEntity user, Transaction transaction) {
        TransactionRepository transactionRepository = new TransactionRepository();

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.info("������� ��������� ���������� � ������������ ������: {}", transaction.getAmount());
            throw new TransactionException("Amount must be greater than zero");
        }
        transaction.setUserId(user.getId());

        try {
            transactionRepository.save(transaction);
            logger.info("���������� ������� ��������� ��� ������������ � id={}", user.getId());
        } catch (SQLException e) {
            logger.error("������ ��� ���������� ����������", e);
            throw new TransactionException("Unknown exception");
        }
    }

    public void delete(UserEntity user, long idTransaction) {
        TransactionRepository transactionRepository = new TransactionRepository();

        try {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(idTransaction);

            if (optionalTransaction.isPresent()) {
                if (!optionalTransaction.get().getUserId().equals(user.getId())) {
                    logger.info("������� ������� ����� ����������: userId={}, ownerId={}", user.getId(), optionalTransaction.get().getUserId());
                    throw new ForbiddenException("You are not allowed to delete this transaction");
                }
            } else {
                return;
            }

            transactionRepository.deleteById(idTransaction);
            logger.info("���������� � id={} �������", idTransaction);
        } catch (SQLException e) {
            logger.error("������ ��� �������� ����������", e);
            throw new TransactionException("Unknown exception");
        }
    }

    private BigDecimal getRevenue(List<Transaction> transactions) {
        BigDecimal revenue = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            if (transaction.getType()) {
                revenue = revenue.add(transaction.getAmount());
            }
        }
        return revenue;
    }

    private BigDecimal getExpenses(List<Transaction> transactions) {
        BigDecimal expenses = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            if (!transaction.getType()) {
                expenses = expenses.add(transaction.getAmount());
            }
        }
        return expenses;
    }

    private TransactionResponse getTransactionResponse(List<Transaction> transactions) {
        BigDecimal revenue = getRevenue(transactions);
        BigDecimal expenses = getExpenses(transactions);
        BigDecimal total = revenue.subtract(expenses);

        return new TransactionResponse(transactions, revenue, expenses, total);
    }
}
