package ru.vsu.amm.java.generator;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.TeaBag;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SqlGeneratorTest {

    @Test
    void selectCorrectColumnTest() {
        SqlGenerator sqlGenerator = new SqlGenerator();
        assertEquals("SELECT id FROM TeaBag;", sqlGenerator.select("id"));
    }

    @Test
    void selectIncorrectColumnTest() {
        SqlGenerator sqlGenerator = new SqlGenerator();
        assertEquals("", sqlGenerator.select("id1"));
    }

    @Test
    void updateEntityTest() {
        SqlGenerator sqlGenerator = new SqlGenerator();
        TeaBag teaBag = new TeaBag(1, "green", 2024, 100);
        assertEquals("Update TeaBag SET name = green, year = 2024, weight = 100 WHERE id = 1;", sqlGenerator.update(teaBag));
    }

    @Test
    void updateEntityNullIdTest() {
        SqlGenerator sqlGenerator = new SqlGenerator();
        TeaBag teaBag = new TeaBag(null, "green", 2024, 100);
        assertEquals("Update TeaBag SET name = green, year = 2024, weight = 100;", sqlGenerator.update(teaBag));
    }

    @Test
    void updateEntityNullTest() {
        SqlGenerator sqlGenerator = new SqlGenerator();
        TeaBag teaBag = new TeaBag(null, null, null, null);
        assertEquals("", sqlGenerator.update(teaBag));
    }
}