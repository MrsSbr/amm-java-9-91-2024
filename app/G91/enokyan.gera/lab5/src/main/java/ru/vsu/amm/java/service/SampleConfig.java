package ru.vsu.amm.java.service;

import ru.vsu.amm.java.annotation.Config;
import ru.vsu.amm.java.annotation.Property;
import ru.vsu.amm.java.enums.MyColor;

@Config(path = "app/G91/enokyan.gera/lab5/src/main/java/ru/vsu/amm/java/config/sample.properties")
public interface SampleConfig {
    @Property("smpl.string")
    String getString();

    @Property("smpl.integer")
    Integer getInteger();

    @Property("smpl.double")
    Double getDouble();

    @Property("smpl.boolean")
    Boolean getBoolean();

    @Property("smpl.mycolor")
    MyColor getMyColor();
}
