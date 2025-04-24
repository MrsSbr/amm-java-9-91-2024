package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Exeption.NotFoundException;
import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.Entities.ShareholderStocks;
import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Repository.StocksRepository;
import ru.vsu.amm.java.Service.Interface.StocksServiceInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class StocksService implements StocksServiceInterface {

    private StocksRepository stocksRepository;
    private ConcurrentHashMap<Integer, Object> locker = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(StocksService.class.getName());

    public StocksService() {
        logger.info("Create service");
        stocksRepository = new StocksRepository();
    }

    @Override
    public List<Stocks> getAll(int limit, int offset) throws SQLException {
        logger.info("Find all stocks");
        return stocksRepository.getAll(limit, offset);
    }

    @Override
    public List<Stocks> getAll(int userId, int limit, int offset) throws SQLException {
        logger.info("Find all stocks by user");
        return stocksRepository.getAll(userId, limit, offset);
    }

    @Override
    public Stocks getById(int stocksId) throws SQLException {
        logger.info("Find stock by id");
        return stocksRepository.get(stocksId)
                .orElseThrow(() -> new NotFoundException("Stock not found"));
    }

    @Override
    public Stocks getByName(String stocksName) throws SQLException {
        logger.info("Find stock by name");
        return stocksRepository.getByName(stocksName)
                .orElseThrow(() -> new NotFoundException("Stock not found"));
    }

    /* @Override
    public void update(int id, int change) throws SQLException {
        logger.info("Update stock count");
        Stocks stock = stocksRepository.get(id)
                .orElseThrow(() -> new NotFoundException("Stock not found"));
        if(stock.getCount()>change) {
            stock.setCount(stock.getCount() - change);
        }
        else {
            throw new UnCorrectDataException("There are not that many stocks available");
        }
    }*/

    @Override
    public void sell(int userId, int stockId, int count) throws SQLException {
        synchronized (locker.computeIfAbsent(stockId, x -> new Object())) {
            logger.info("Sell stock");
            ShareholderStocks shareholderStock = stocksRepository.get(userId, stockId)
                    .orElseThrow(() -> new NotFoundException("Stock not found"));
            Stocks stock = stocksRepository.get(stockId)
                    .orElseThrow(() -> new NotFoundException("Stock not found"));
            if (shareholderStock.getCount() < count) {
                throw new UnCorrectDataException("There are not that many stocks available");
            }
            if (shareholderStock.getCount() == count) {
                stocksRepository.delete(userId, stockId);
            } else {
                shareholderStock.setCount(shareholderStock.getCount() - count);
                stocksRepository.update(shareholderStock);
            }
            stock.setCount(stock.getCount() + count);
            stocksRepository.update(stock);
            logger.info("Sell success");
        }
    }

    @Override
    public void buy(int userId, int stockId, int count) throws SQLException {
        synchronized (locker.computeIfAbsent(stockId, x -> new Object())) {
            logger.info("Buy stock");
            Stocks stock = stocksRepository.get(stockId)
                    .orElseThrow(() -> new NotFoundException("Stock not found"));
            Optional<ShareholderStocks> stockOp = stocksRepository.get(userId, stockId);
            if (stock.getCount() < count) {
                throw new UnCorrectDataException("There are not that many stocks available");
            }
            stock.setCount(stock.getCount() - count);
            stocksRepository.update(stock);
            if (stockOp.isEmpty()) {
                stocksRepository.create(new ShareholderStocks(userId, stockId, count));
            } else {
                ShareholderStocks shareholderStock = stockOp.get();
                shareholderStock.setCount(shareholderStock.getCount() + count);
                stocksRepository.update(shareholderStock);
            }
            logger.info("Buy success");
        }
    }

    @Override
    public int count(int size) throws SQLException {
        logger.info("Find count");
        return (stocksRepository.count()+size-1) / size;
    }

    @Override
    public int count(int userId,int size) throws SQLException {
        logger.info("Find count");
        return (stocksRepository.count(userId)+size-1) / size;
    }
}
