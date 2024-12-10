package ru.vsu.amm.java.SizeType.Abstract;

public abstract class BaseSizeType {

    private final int bitSize;

    public BaseSizeType(int byteSize) {
        this.bitSize = byteSize;
    }

    public int getSize() {
        return bitSize;
    }
}