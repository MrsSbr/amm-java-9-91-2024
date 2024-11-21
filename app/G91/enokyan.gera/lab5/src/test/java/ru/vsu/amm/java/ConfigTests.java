package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.enums.CarBrand;
import ru.vsu.amm.java.service.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ConfigTests {
    private static TestConfig service;

    @BeforeAll
    static void initialize() {
        service = ConfigService.load(TestConfig.class);
    }

    @Test
    public void testBadTestConfigOne() {
        assertThrows(IllegalArgumentException.class, () -> ConfigService.load(BadTestConfigOne.class));
    }

    @Test
    public void testBadTestConfigTwo() {
        assertThrows(IllegalArgumentException.class, () -> ConfigService.load(BadTestConfigTwo.class));
    }

    @Test
    public void testBadTestConfigThree() {
        assertThrows(IllegalArgumentException.class, () -> ConfigService.load(BadTestConfigThree.class));
    }

    @Test
    public void testBadMethodOne() {
        assertThrows(IllegalArgumentException.class, () -> service.badMethodOne());
    }

    @Test
    public void testBadMethodTwo() {
        assertThrows(IllegalArgumentException.class, () -> service.badMethodTwo());
    }

    @Test
    public void testBadMethodThree() {
        assertThrows(IllegalArgumentException.class, () -> service.badMethodThree());
    }

    @Test
    public void testGetBadInteger()
    {
        assertThrows(NumberFormatException.class, () -> service.getBadInteger());
    }

    @Test
    public void testGetBadDouble()
    {
        assertThrows(NumberFormatException.class, () -> service.getBadDouble());
    }

    @Test
    public void testGetBadCarBrand()
    {
        assertThrows(IllegalArgumentException.class, () -> service.getBadCarBrand());
    }

    @Test
    public void testGetString()
    {
        var str = service.getString();

        assertEquals("quack-quack", str);
    }

    @Test
    public void testGetInteger()
    {
        var str = service.getInteger();

        assertEquals(420, str);
    }



    @Test
    public void testGetDouble()
    {
        var str = service.getDouble();

        assertEquals(4.2, str);
    }

    @Test
    public void testGetBoolean()
    {
        var str = service.getBoolean();

        assertEquals(true, str);
    }

    @Test
    public void testGetCarBrand()
    {
        var str = service.getCarBrand();

        assertEquals(CarBrand.BMW, str);
    }
}
