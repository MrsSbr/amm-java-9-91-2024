package ru.vsu.amm.java.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.amm.java.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {
    private List<Transaction> transactions;
    private BigDecimal revenue;
    private BigDecimal expenses;
    private BigDecimal total;
}
