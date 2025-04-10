package ru.vsu.amm.java.Repository.Entities;

public class Stocks {
    private Integer id;
    private Double price;
    private Integer count;
    private String name;
    private Double dividends;

    public Stocks(Integer id, Double price, Integer count, String name, Double dividends) {
        this.id = id;
        this.price = price;
        this.count = count;
        this.name = name;
        this.dividends = dividends;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDividends() {
        return dividends;
    }

    public void setDividends(Double dividends) {
        this.dividends = dividends;
    }
}
