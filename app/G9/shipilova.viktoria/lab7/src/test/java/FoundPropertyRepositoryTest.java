import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.connection.DataSourceProvider;
import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enams.ReturnStatus;
import ru.vsu.amm.java.repository.FoundPropertyRepository;
import util.TestDatabaseInitializer;
import util.TestDataSourceProvider;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FoundPropertyRepositoryTest {

    private FoundPropertyRepository foundPropertyRepository;
    private TestDatabaseInitializer dbInitializer;

    @BeforeEach
    public void setUp() throws SQLException, IOException {
        DataSourceProvider.setTestDataSource(TestDataSourceProvider.getTestDataSource());

        dbInitializer = new TestDatabaseInitializer(TestDataSourceProvider.getTestDataSource());
        dbInitializer.initializeDatabase("src/test/resources/CREATE.sql", "src/test/resources/INSERT.sql");

        foundPropertyRepository = new FoundPropertyRepository();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (var conn = TestDataSourceProvider.getTestDataSource().getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("DROP ALL OBJECTS");
        }

        DataSourceProvider.resetTestDataSource();
    }

    @Test
    public void testGetById() {
        FoundProperty foundProperty = foundPropertyRepository.getById(1L);
        assertNotNull(foundProperty);
        assertEquals(1L, foundProperty.getId());
        assertEquals("NOT_RETURNED", foundProperty.getReturnStatus().name());
        assertEquals("Park", foundProperty.getPlaceOfFinding());
        assertEquals("Phone", foundProperty.getDescription());
    }

    @Test
    public void testGetAll() {
        List<FoundProperty> foundProperties = foundPropertyRepository.getAll();
        assertNotNull(foundProperties);
        assertEquals(2, foundProperties.size());
    }

    @Test
    public void testSave() {
        FoundProperty newProperty = new FoundProperty();
        PropertyType propertyType = new PropertyType();
        propertyType.setId(11L);
        newProperty.setPropertyType(propertyType);
        newProperty.setDateOfFinding(LocalDate.now());
        newProperty.setTimeOfFinding(LocalTime.now());
        newProperty.setReturnStatus(ReturnStatus.NOT_RETURNED);
        newProperty.setPlaceOfFinding("Cafe");
        newProperty.setDescription("Lost Bag");
        User user = new User();
        user.setId(1L);
        newProperty.setUser(user);

        foundPropertyRepository.save(newProperty);

        List<FoundProperty> foundProperties = foundPropertyRepository.getAll();
        assertEquals(3, foundProperties.size());
    }

    @Test
    public void testUpdate() {
        FoundProperty foundProperty = foundPropertyRepository.getById(1L);
        assertNotNull(foundProperty);

        foundProperty.setPlaceOfFinding("Updated Place");
        foundProperty.setDescription("Updated Description");
        foundProperty.setReturnStatus(ReturnStatus.RETURNED);

        foundPropertyRepository.update(foundProperty);

        FoundProperty updatedProperty = foundPropertyRepository.getById(1L);
        assertEquals("Updated Place", updatedProperty.getPlaceOfFinding());
        assertEquals("Updated Description", updatedProperty.getDescription());
        assertEquals(ReturnStatus.RETURNED, updatedProperty.getReturnStatus());
    }

    @Test
    public void testDelete() {
        foundPropertyRepository.delete(1L);

        FoundProperty deletedProperty = foundPropertyRepository.getById(1L);
        assertNull(deletedProperty);

        List<FoundProperty> foundProperties = foundPropertyRepository.getAll();
        assertEquals(1, foundProperties.size());
    }
}
