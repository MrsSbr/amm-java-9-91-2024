package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Exeption.NotFoundException;
import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Repository.StocksRepository;
import ru.vsu.amm.java.Service.Interface.StocksServiceInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class StocksService implements StocksServiceInterface {

    private StocksRepository stocksRepository;
    private static final Logger logger = Logger.getLogger(StocksService.class.getName());

    public StocksService(){
        logger.info("Create service");
        stocksRepository = new StocksRepository();
    }

    @Override
    public List<Stocks> getAll() throws SQLException {
        logger.info("Find all stocks");
        return stocksRepository.getAll();
    }

    @Override
    public Stocks getById(int id) throws SQLException {
        logger.info("Find stock by id");
        return stocksRepository.get(id)
                .orElseThrow(() -> new NotFoundException("Stock not found"));
    }

    @Override
    public Stocks getByName(String name) throws SQLException {
        logger.info("Find stock by name");
        return stocksRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("Stock not found"));
    }

    @Override
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
    }
}
