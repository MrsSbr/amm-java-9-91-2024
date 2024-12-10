package ru.vsu.amm.java.SizeType;

public enum SizeType {
    BIT(1),
    BYTE(8),
    KILOBYTE(8 * 1024),
    MEGABYTE(8 * 1024 * 1024);

    private final int sizeInBits;

    SizeType(int sizeInBits) {
        this.sizeInBits = sizeInBits;
    }

    public int getSizeInBits() {
        return sizeInBits;
    }
}
