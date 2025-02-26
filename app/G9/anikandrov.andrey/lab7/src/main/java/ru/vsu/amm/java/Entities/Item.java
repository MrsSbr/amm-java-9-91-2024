package Entities;


public class Item {
    private long itemID;
    private String name;
    private String info;


    public Item() {}

    public Item(long itemID, String name, String info) {
        this.itemID = itemID;
        this.name = name;
        this.info = info;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
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
}
