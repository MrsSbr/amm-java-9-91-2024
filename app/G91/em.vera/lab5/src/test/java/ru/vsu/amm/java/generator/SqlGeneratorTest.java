package ru.vsu.amm.java.generator;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.TeaBag;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SqlGeneratorTest {

    @Test
    void selectCorrectColumnTest() {
        assertEquals("SELECT name, year FROM TeaBag;", SqlGenerator.select());
    }
    @Test
    void updateEntityTest() {
        TeaBag teaBag = new TeaBag(1, "green", 2024, 100);
        assertEquals("Update TeaBag SET name = 'green', year = 2024, weight = 100 WHERE id = 1;", SqlGenerator.update(teaBag));
    }

    @Test
    void updateEntityNullIdTest() {
        TeaBag teaBag = new TeaBag(null, "green", 2024, 100);
        assertEquals("Update TeaBag SET name = 'green', year = 2024, weight = 100;", SqlGenerator.update(teaBag));
    }

    @Test
    void updateEntityNullTest() {
        TeaBag teaBag = new TeaBag(null, null, null, null);
        assertEquals("", SqlGenerator.update(teaBag));
    }
}