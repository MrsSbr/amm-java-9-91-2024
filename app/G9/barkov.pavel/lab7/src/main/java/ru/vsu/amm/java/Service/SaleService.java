package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Exeption.NotFoundException;
import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.Entities.ShareholderStocks;
import ru.vsu.amm.java.Repository.ShareholderStocksRepository;
import ru.vsu.amm.java.Service.Interface.SaleServiceInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class SaleService implements SaleServiceInterface {

    private ShareholderStocksRepository saleRepository;
    private static final Logger logger = Logger.getLogger(SaleService.class.getName());

    public SaleService(){
        logger.info("Create service");
        saleRepository = new ShareholderStocksRepository();
    }
    @Override
    public List<ShareholderStocks> getAll() throws SQLException {
        logger.info("Find all stocks");
        return saleRepository.getAll();
    }

    @Override
    public void Sell(int userId, int stockId, int count) throws SQLException {
        logger.info("Sell stock");
        ShareholderStocks stock = saleRepository.get(userId,stockId)
                .orElseThrow(() -> new NotFoundException("Stock not found"));
        if(stock.getCount() < count) {
            throw new UnCorrectDataException("There are not that many stocks available");
        }
        if(stock.getCount() == count) {
            saleRepository.delete(userId, stockId);
        }
        else {
            stock.setCount(stock.getCount() - count);
            saleRepository.update(stock);
        }
        logger.info("Sell success");
    }

    @Override
    public void Buy(int userId, int stockId, int count) throws SQLException {
        logger.info("Buy stock");
        Optional<ShareholderStocks> stockOp = saleRepository.get(userId,stockId);
        if(stockOp.isEmpty()) {
            saleRepository.create(new ShareholderStocks(userId,stockId,count));
        }
        else{
            ShareholderStocks stock = stockOp.get();
            stock.setCount(stock.getCount()+count);
            saleRepository.update(stock);
        }
        logger.info("Buy success");
    }
}
