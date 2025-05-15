import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vsu.amm.java.enams.PropertyTypeName;
import ru.vsu.amm.java.enams.ReturnStatus;
import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.FoundPropertyRepository;
import ru.vsu.amm.java.repository.PropertyTypeRepository;
import ru.vsu.amm.java.services.FoundPropertyService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;


public class FoundPropertyServiceTest {

    @Mock
    private FoundPropertyRepository foundPropertyRepository;

    @Mock
    private PropertyTypeRepository propertyTypeRepository;

    @InjectMocks
    private FoundPropertyService foundPropertyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFoundProperty() {
        User user = new User();
        user.setLogin("testUser");

        String propType = "KEYS";
        String placeOfFinding = "Park";
        String description = "Keychain with 3 keys";

        PropertyType propertyType = new PropertyType();
        propertyType.setPropertyTypeName(PropertyTypeName.KEYS);

        when(propertyTypeRepository.getByName(PropertyTypeName.valueOf(propType))).thenReturn(propertyType);

        foundPropertyService.addFoundProperty(propType, placeOfFinding, description, user);

        verify(foundPropertyRepository, times(1)).save(any(FoundProperty.class));
    }

    @Test
    public void testGetNotReturnedProperties() {
        PropertyType propertyType = new PropertyType();
        propertyType.setPropertyTypeName(PropertyTypeName.KEYS);

        FoundProperty property1 = new FoundProperty();
        property1.setReturnStatus(ReturnStatus.NOT_RETURNED);
        property1.setPropertyType(propertyType);

        FoundProperty property2 = new FoundProperty();
        property2.setReturnStatus(ReturnStatus.RETURNED);
        property2.setPropertyType(propertyType);

        when(foundPropertyRepository.getAll()).thenReturn(Arrays.asList(property1, property2));
        when(propertyTypeRepository.getById(anyLong())).thenReturn(propertyType);


        List<FoundProperty> result = foundPropertyService.getNotReturnedProperties();

        assertEquals(1, result.size());
        assertEquals(ReturnStatus.NOT_RETURNED, result.get(0).getReturnStatus());
    }

    @Test
    public void testUpdatePropertyStatusToReturned_Success() {
        long propertyId = 1L;
        FoundProperty property = new FoundProperty();
        property.setId(propertyId);
        property.setReturnStatus(ReturnStatus.NOT_RETURNED);

        when(foundPropertyRepository.getById(propertyId)).thenReturn(property);

        boolean result = foundPropertyService.updatePropertyStatusToReturned(propertyId);

        assertTrue(result);
        assertEquals(ReturnStatus.RETURNED, property.getReturnStatus());
        verify(foundPropertyRepository, times(1)).update(property);
    }

    @Test
    public void testUpdatePropertyStatusToReturned_NotFound() {
        long propertyId = 1L;

        when(foundPropertyRepository.getById(propertyId)).thenReturn(null);

        boolean result = foundPropertyService.updatePropertyStatusToReturned(propertyId);

        assertFalse(result);
        verify(foundPropertyRepository, never()).update(any(FoundProperty.class));
    }
}
