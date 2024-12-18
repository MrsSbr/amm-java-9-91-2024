import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ru.vsu.amm.java.builder.SQLQueryBuilder;
import ru.vsu.amm.java.entity.MyEntity;


public class SQLQueryBuilderTest {
    private SQLQueryBuilder queryBuilder;
    private MyEntity entity;

    @BeforeEach
    public void setUp() {
        queryBuilder = new SQLQueryBuilder(MyEntity.class);
        entity = new MyEntity();
        entity.setId(1);
        entity.setName("Vika");
        entity.setAge(20);
    }

    @Test
    public void testGenerateInsertQuery() throws IllegalAccessException {
        String expectedQuery = "INSERT INTO MyTable (id, name, age) VALUES ('1', 'Vika', '20')";
        String actualQuery = queryBuilder.generateInsertQuery(entity);
        assertEquals(expectedQuery, actualQuery, "Generated INSERT query is not correct");
    }

    @Test
    public void testGenerateSelectQuery() {
        String expectedQuery = "SELECT * FROM MyTable WHERE id = 1";
        String actualQuery = queryBuilder.generateSelectQuery("id = 1");
        assertEquals(expectedQuery, actualQuery, "Generated SELECT query is not correct");
    }

    @Test
    public void testGenerateUpdateQuery() throws IllegalAccessException {
        String expectedQuery = "UPDATE MyTable SET id = '1', name = 'Vika', age = '20' WHERE id = 1";
        String actualQuery = queryBuilder.generateUpdateQuery(entity, "id = 1");
        assertEquals(expectedQuery, actualQuery, "Generated UPDATE query is not correct");
    }

    @Test
    public void testGenerateDeleteQuery() {
        String expectedQuery = "DELETE FROM MyTable WHERE id = 1";
        String actualQuery = queryBuilder.generateDeleteQuery("id = 1");
        assertEquals(expectedQuery, actualQuery, "Generated DELETE query is not correct");
    }
}
