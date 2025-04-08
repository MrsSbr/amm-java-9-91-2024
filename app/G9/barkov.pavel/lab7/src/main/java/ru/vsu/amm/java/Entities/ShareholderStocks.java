package ru.vsu.amm.java.Entities;

public class ShareholderStocks {
    private Integer shareholderId;
    private Integer stocksId;
    private Integer count;

    public ShareholderStocks(Integer shareholderId, Integer stocksId, Integer count) {
        this.shareholderId = shareholderId;
        this.stocksId = stocksId;
        this.count = count;
    }

    public Integer getShareholderId() {
        return shareholderId;
    }

    public void setShareholderId(Integer shareholderId) {
        this.shareholderId = shareholderId;
    }

    public Integer getStocksId() {
        return stocksId;
    }

    public void setStocksId(Integer stocksId) {
        this.stocksId = stocksId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
