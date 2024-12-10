package ru.vsu.amm.java.SizeType;

import ru.vsu.amm.java.SizeType.Abstract.BaseSizeType;

public class KilobyteSizeType extends BaseSizeType {
    public KilobyteSizeType() {
        super(1024*1024);
    }
}
