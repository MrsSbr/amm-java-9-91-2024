package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.Housing;

public class RealEstate {
    private Long id;
    private Housing type;
    private String address;
    private int maximumNumberOfGuests;
    private String rules;
    private String imageName;
    private Long price;

    public RealEstate(Long id, Housing type, String address, int maximumNumberOfGuests,
                      String rules, String imageName, Long price) {
        this.id = id;
        this.type = type;
        this.address = address;
        this.maximumNumberOfGuests = maximumNumberOfGuests;
        this.rules = rules;
        this.imageName = imageName;
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public Housing getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public int getMaximumNumberOfGuests() {
        return maximumNumberOfGuests;
    }

    public String getRules() {
        return rules;
    }

    public Long getId() {
        return id;
    }
    public Long getPrice() {return price;  }
}
