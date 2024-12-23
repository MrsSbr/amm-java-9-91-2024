package ru.vsu.amm.java.Enums;

public enum RestaurantNames {
    BISTRO(0),
    GRILL(1),
    CAFE(2),
    BRUNCH(3),
    TAVERN(4),
    DINER(5),
    BAKERY(6),
    PUB(7),
    LOUNGE(8),
    STEAKHOUSE(9);

    private int id;

    RestaurantNames(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RestaurantNames fromId(int id) {
        for (RestaurantNames name : RestaurantNames.values()) {
            if (name.getId() == id) {
                return name;
            }
        }
        return null;
    }
}
