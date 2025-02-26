package Entities;

public class Habitation {
    private long habitationID;
    private String name;
    private String info;
    private Integer Price;


    public Habitation () {}

    public Habitation(long habitationID, String name, String info, Integer price) {
        this.habitationID = habitationID;
        this.name = name;
        this.info = info;
        this.Price = price;
    }

    public long getHabitationID() {
        return habitationID;
    }

    public void setHabitationID(long habitationID) {
        this.habitationID = habitationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        this.Price = price;
    }
}
