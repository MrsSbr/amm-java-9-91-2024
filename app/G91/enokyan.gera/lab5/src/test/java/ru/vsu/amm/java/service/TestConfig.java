package ru.vsu.amm.java.service;

import ru.vsu.amm.java.annotation.Config;
import ru.vsu.amm.java.annotation.Property;
import ru.vsu.amm.java.enums.CarBrand;

import java.time.LocalDate;

@Config(path = "src/test/java/ru/vsu/amm/java/config/test.properties")
public interface TestConfig {
    String badMethodOne();

    @Property("test.bad")
    String badMethodTwo();

    @Property("test.local-date")
    LocalDate badMethodThree();

    @Property("test.bad-integer")
    Integer getBadInteger();

    @Property("test.bad-double")
    Double getBadDouble();

    @Property("test.bad-car-brand")
    CarBrand getBadCarBrand();

    @Property("test.string")
    String getString();

    @Property("test.integer")
    Integer getInteger();

    @Property("test.double")
    Double getDouble();

    @Property("test.boolean")
    Boolean getBoolean();

    @Property("test.car-brand")
    CarBrand getCarBrand();
}