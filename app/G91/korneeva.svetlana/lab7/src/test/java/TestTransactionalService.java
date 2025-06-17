import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Transaction;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.entity.response.TransactionResponse;
import ru.vsu.amm.java.enums.Category;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.exception.TransactionException;
import ru.vsu.amm.java.repository.TransactionRepository;
import ru.vsu.amm.java.service.TransactionService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestTransactionalService {
    private TransactionService transactionService;
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void findAll() throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(1L);

        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setUserId(1L);
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setType(true);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setUserId(1L);
        transaction2.setAmount(new BigDecimal("50.00"));
        transaction2.setType(false);

        List<Transaction> transactions = List.of(transaction1, transaction2);

        when(transactionRepository.findByUserId(user.getId())).thenReturn(transactions);

        TransactionResponse response = transactionService.findAll(user);

        assertNotNull(response);
        assertEquals(2, response.getTransactions().size());
        assertEquals(new BigDecimal("100.00"), response.getRevenue());
        assertEquals(new BigDecimal("50.00"), response.getExpenses());
        assertEquals(new BigDecimal("50.00"), response.getTotal());
    }

    @Test
    void findByTimeRange() throws SQLException {
        UserEntity user = new UserEntity(1L, "test@example.com", "test");
        LocalDateTime start = LocalDateTime.now().minusDays(30);
        LocalDateTime end = LocalDateTime.now();

        Transaction transaction = new Transaction(1L, 1L, new BigDecimal("100.00"), true, end.minusDays(1), Category.EDUCATION);

        when(transactionRepository.findByUserIdAndTimeRange(1L, start, end))
                .thenReturn(List.of(transaction));

        TransactionResponse response = transactionService.findByTimeRange(user, start, end);

        assertNotNull(response);
        assertEquals(1, response.getTransactions().size());
        assertEquals(new BigDecimal("100.00"), response.getRevenue());
        assertEquals(BigDecimal.ZERO, response.getExpenses());
        assertEquals(new BigDecimal("100.00"), response.getTotal());
    }

    @Test
    void findByTimeRangeWithException() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusDays(1);

        assertThrows(TransactionException.class, () -> transactionService.findByTimeRange(user, start, end));
    }

    @Test
    void save() throws Exception {
        UserEntity user = new UserEntity(1L, "test@example.com", "test");
        Transaction transaction = new Transaction(null, null, new BigDecimal("150.00"), true, LocalDateTime.now(), Category.OTHER);

        doNothing().when(transactionRepository).save(any(Transaction.class));

        transactionService.save(user, transaction);

        assertEquals(1L, transaction.getUserId());
        verify(transactionRepository).save(transaction);
    }

    @Test
    void saveWithZeroDecimal() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.ZERO);

        assertThrows(TransactionException.class, () -> transactionService.save(user, transaction));
    }

    @Test
    void delete_shouldSuccessfullyDeleteOwnedTransaction() throws Exception {
        UserEntity user = new UserEntity(1L, "test@example.com", "test");
        long transactionId = 5L;
        Transaction transaction = new Transaction(transactionId, 1L, new BigDecimal("50.00"), false, LocalDateTime.now(), Category.OTHER);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).deleteById(transactionId);

        transactionService.delete(user, transactionId);

        verify(transactionRepository).deleteById(transactionId);
    }

    @Test
    void deleteWithForbiddenException() throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(1L);

        long transactionId = 100L;

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setUserId(2L);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        assertThrows(ForbiddenException.class, () -> transactionService.delete(user, transactionId));
    }
}
