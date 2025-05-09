package ru.vsu.amm.java.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Exeption.NotFoundException;
import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.Entities.ShareholderStocks;
import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Repository.StocksRepository;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StocksServiceUnitTest {

    private StocksService stocksService;
    private StocksRepository stocksRepository;
    @BeforeEach
    void setUp() {
        stocksRepository = mock(StocksRepository.class);
        stocksService = new StocksService(stocksRepository);
    }
    @Test
    void sellStocksPositiveResult() throws SQLException {
        Stocks sellStock = new Stocks(1,10d,10,"MMM",1d);
        ShareholderStocks userStock = new ShareholderStocks(1,1,5);
        int stocksId = 1;
        int userId = 1;
        when(stocksRepository.get(stocksId)).thenReturn(Optional.of(sellStock));
        when(stocksRepository.get(userId,stocksId)).thenReturn(Optional.of(userStock));
        stocksService.sell(userId,stocksId,2);
        assertEquals(sellStock.getCount(),12);
        assertEquals(userStock.getCount(),3);
    }

    @Test
    void sellStocksNotFoundResult() throws SQLException {
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> stocksService.sell(1,1,1)
        );
        assertEquals("Stock not found", exception.getMessage());
    }

    @Test
    void sellStocksUnCorrectDataResult() throws SQLException {
        Stocks sellStock = new Stocks(1,10d,10,"MMM",1d);
        ShareholderStocks userStock = new ShareholderStocks(1,1,5);
        int stocksId = 1;
        int userId = 1;
        when(stocksRepository.get(stocksId)).thenReturn(Optional.of(sellStock));
        when(stocksRepository.get(userId,stocksId)).thenReturn(Optional.of(userStock));
        UnCorrectDataException exception = assertThrows(
                UnCorrectDataException.class,
                () -> stocksService.sell(userId,stocksId,10)
        );
        assertEquals("There are not that many stocks available", exception.getMessage());
    }

    @Test
    void buyStocksPositiveResult() throws SQLException {
        Stocks sellStock = new Stocks(1,10d,10,"MMM",1d);
        ShareholderStocks userStock = new ShareholderStocks(1,1,5);
        int stocksId = 1;
        int userId = 1;
        when(stocksRepository.get(stocksId)).thenReturn(Optional.of(sellStock));
        when(stocksRepository.get(userId,stocksId)).thenReturn(Optional.of(userStock));
        stocksService.buy(userId,stocksId,2);
        assertEquals(sellStock.getCount(),8);
        assertEquals(userStock.getCount(),7);
    }

    @Test
    void buyStocksNotFoundResult() throws SQLException {
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> stocksService.buy(1,1,1)
        );
        assertEquals("Stock not found", exception.getMessage());
    }

    @Test
    void buyStocksUnCorrectDataResult() throws SQLException {
        Stocks sellStock = new Stocks(1,10d,10,"MMM",1d);
        ShareholderStocks userStock = new ShareholderStocks(1,1,5);
        int stocksId = 1;
        int userId = 1;
        when(stocksRepository.get(stocksId)).thenReturn(Optional.of(sellStock));
        when(stocksRepository.get(userId,stocksId)).thenReturn(Optional.of(userStock));
        UnCorrectDataException exception = assertThrows(
                UnCorrectDataException.class,
                () -> stocksService.buy(userId,stocksId,100)
        );
        assertEquals("There are not that many stocks available", exception.getMessage());
    }

    @Test
    void countStocksPositiveResult() throws SQLException {
        when(stocksRepository.count()).thenReturn(12);
        int count = stocksService.count(5);
       assertEquals(count,3);
    }

}