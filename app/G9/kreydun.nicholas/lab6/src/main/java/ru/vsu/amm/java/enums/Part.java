package ru.vsu.amm.java.enums;

public enum Part {
    PART1(1),
    PART2(2),
    PART3(3),
    MODULE(0);

    private final int productionTime;

    Part(int productionTime) {
        this.productionTime = productionTime;
    }

    public int getProductionTime() {
        return productionTime;
    }
}
