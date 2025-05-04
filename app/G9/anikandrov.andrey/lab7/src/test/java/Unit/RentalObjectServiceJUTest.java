package Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vsu.amm.java.Entities.RentalObjectEntity;
import ru.vsu.amm.java.Enums.ObjectType;
import ru.vsu.amm.java.Repository.RentalObjectRepository;
import ru.vsu.amm.java.Services.RentalObjectService;

import ru.vsu.amm.java.Exception.DatabaseException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class RentalObjectServiceJUTest {
    private RentalObjectService rentalObjectService;
    private RentalObjectRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(RentalObjectRepository.class);
        rentalObjectService = new RentalObjectService(repository);
    }

    @Test
    void getObjectsByTypeSuccess() throws Exception {
        RentalObjectEntity eventObj = new RentalObjectEntity(1L, "Hike", ObjectType.EVENT, "", 100);
        RentalObjectEntity itemObj = new RentalObjectEntity(2L, "Backpack", ObjectType.ITEM, "", 50);
        when(repository.findAll()).thenReturn(Arrays.asList(eventObj, itemObj));

        List<RentalObjectEntity> result = rentalObjectService.getObjectsByType(ObjectType.EVENT);

        assertEquals(1, result.size());
        assertEquals(ObjectType.EVENT, result.get(0).getObjectType());
        verify(repository).findAll();
    }

    @Test
    void getObjectsByTypeEmptyResult() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<RentalObjectEntity> result = rentalObjectService.getObjectsByType(ObjectType.HABITATION);

        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void getObjectsByTypeDatabaseError() throws Exception {
        when(repository.findAll()).thenThrow(new SQLException("Database failure"));

        DatabaseException exception = assertThrows(DatabaseException.class, () ->
                rentalObjectService.getObjectsByType(ObjectType.EVENT)
        );
        assertEquals("Error with getting objects by type", exception.getMessage());
    }

    @Test
    void getObjectsForAgreementsSuccess() throws Exception {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 2L);
        RentalObjectEntity obj1 = new RentalObjectEntity(1L, "Backpack", ObjectType.ITEM, "", 10);
        RentalObjectEntity obj2 = new RentalObjectEntity(2L, "Hike", ObjectType.EVENT, "", 20);

        when(repository.findById(1L)).thenReturn(Optional.of(obj1));
        when(repository.findById(2L)).thenReturn(Optional.of(obj2));

        List<RentalObjectEntity> result = rentalObjectService.getObjectsForAgreements(ids);

        assertEquals(2, result.size());
        verify(repository).findById(1L);
        verify(repository).findById(2L);
    }

    @Test
    void getObjectsForAgreementsNotFound() throws Exception {
        List<Long> ids = Arrays.asList(1L, 999L);
        RentalObjectEntity obj1 = new RentalObjectEntity(1L, "Obj1", ObjectType.ITEM, "Info", 10);

        when(repository.findById(1L)).thenReturn(Optional.of(obj1));
        when(repository.findById(999L)).thenReturn(Optional.empty());

        List<RentalObjectEntity> result = rentalObjectService.getObjectsForAgreements(ids);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getObjectID());
    }

    @Test
    void getObjectsForAgreementsDatabaseError() throws Exception {
        List<Long> ids = Collections.singletonList(1L);
        when(repository.findById(anyLong())).thenThrow(new SQLException("DB error"));

        assertThrows(DatabaseException.class, () ->
                rentalObjectService.getObjectsForAgreements(ids)
        );
    }
}
