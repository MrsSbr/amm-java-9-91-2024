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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse findAll(UserEntity user) {
        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findByUserId(user.getId());
        } catch (SQLException e) {
            logger.error("Ошибка при получении всех транзакций для пользователя с id={}", user.getId(), e);
            throw new TransactionException("Unknown exception");
        }

        logger.info("Найдено {} транзакций для пользователя с id={}", transactions.size(), user.getId());
        return getTransactionResponse(transactions);
    }

    public TransactionResponse findByTimeRange(UserEntity user, String start, String end) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (start != null && !start.isEmpty()) {
            startDate = LocalDate.parse(start).atStartOfDay();
        }

        if (end != null && !end.isEmpty()) {
            endDate = LocalDate.parse(end).atTime(23, 59, 59);
        }

        if (startDate == null || endDate == null) {
            return findAll(user);
        }

        if (startDate.isAfter(endDate)) {
            logger.info("Начальная дата позже конечной: {} > {}", start, end);
            throw new TransactionException("Начальная дата позже конечной");
        }

        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findByUserIdAndTimeRange(user.getId(), startDate, endDate);
            logger.info("Найдено {} транзакций в интервале {} - {} для пользователя с id={}", transactions.size(), startDate, endDate, user.getId());
        } catch (SQLException e) {
            logger.error("Ошибка при получении транзакций по диапазону времени", e);
            throw new TransactionException("Unknown exception");
        }

        return getTransactionResponse(transactions);
    }

    public void save(UserEntity user, Transaction transaction) {
        if (transaction.getAmount().scale() > 2) {
            throw new TransactionException("Не больше 2 чисел после зпятой");
        }

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.info("Попытка сохранить транзакцию с некорректной суммой: {}", transaction.getAmount());
            throw new TransactionException("Amount must be greater than zero");
        }
        transaction.setUserId(user.getId());

        try {
            transactionRepository.save(transaction);
            logger.info("Транзакция успешно сохранена для пользователя с id={}", user.getId());
        } catch (SQLException e) {
            logger.error("Ошибка при сохранении транзакции", e);
            throw new TransactionException("Unknown exception");
        }
    }

    public void delete(UserEntity user, long idTransaction) {
        try {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(idTransaction);

            if (optionalTransaction.isPresent()) {
                if (!optionalTransaction.get().getUserId().equals(user.getId())) {
                    logger.info("Попытка удалить чужую транзакцию: userId={}, ownerId={}", user.getId(), optionalTransaction.get().getUserId());
                    throw new ForbiddenException("You are not allowed to delete this transaction");
                }
            } else {
                return;
            }

            transactionRepository.deleteById(idTransaction);
            logger.info("Транзакция с id={} удалена", idTransaction);
        } catch (SQLException e) {
            logger.error("Ошибка при удалении транзакции", e);
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
