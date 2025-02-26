import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Department;
import ru.vsu.amm.java.Winner;
import ru.vsu.amm.java.WinnerService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WinnerApplicationTest {

    private WinnerService winnerService;

    @BeforeEach
    void init() {
        winnerService = new WinnerService();
    }


    @Test
    void testGetDepartmentsWithMostWins() {
        List<Winner> winners = Arrays.asList(
                new Winner(2020, "John", Department.ENGINEERING),
                new Winner(2021, "Mary", Department.ENGINEERING),
                new Winner(2022, "Peter", Department.SALES)
        );

        List<Department> result = winnerService.getDepartmentsWithMostWins(winners);
        assertEquals(1, result.size());
        assertEquals(Department.ENGINEERING, result.get(0));
    }

    @Test
    void testGetDepartmentsWithMostWinsMultiple() {
        List<Winner> winners = Arrays.asList(
                new Winner(2020, "John", Department.ENGINEERING),
                new Winner(2021, "Mary", Department.SALES),
                new Winner(2022, "Peter", Department.ENGINEERING),
                new Winner(2023, "Ann", Department.SALES)
        );

        List<Department> result = winnerService.getDepartmentsWithMostWins(winners);
        assertEquals(2, result.size());
        assertTrue(result.contains(Department.ENGINEERING));
        assertTrue(result.contains(Department.SALES));
    }

    @Test
    void testGetDepartmentsWithMostWinsEmpty() {
        List<Winner> winners = Arrays.asList();
        List<Department> result = winnerService.getDepartmentsWithMostWins(winners);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUniqueWinnersWithDuplicates() {
        List<Winner> winners = Arrays.asList(
                new Winner(2020, "John", Department.ENGINEERING),
                new Winner(2021, "John", Department.ENGINEERING),
                new Winner(2022, "Mary", Department.SALES)
        );

        List<String> result = winnerService.getUniqueWinners(winners);
        assertEquals(2, result.size());
        assertTrue(result.contains("John"));
        assertTrue(result.contains("Mary"));
    }

    @Test
    void testGetOneTimeWinnersCount() {
        List<Winner> winners = Arrays.asList(
                new Winner(2020, "John", Department.ENGINEERING),
                new Winner(2021, "John", Department.ENGINEERING),
                new Winner(2022, "Mary", Department.SALES),
                new Winner(2023, "Peter", Department.HR)
        );

        int result = winnerService.getOneTimeWinnersCount(winners);
        assertEquals(2, result);
    }

    @Test
    void testGetOneTimeWinnersCountEmpty() {
        List<Winner> winners = Arrays.asList();
        int result = winnerService.getOneTimeWinnersCount(winners);
        assertEquals(0, result);
    }

    @Test
    void testGetDepartmentsWithMostWins_NullList() {
        assertThrows(NullPointerException.class, () ->
                winnerService.getDepartmentsWithMostWins(null));
    }

    @Test
    void testGetUniqueWinnersNullList() {
        assertThrows(NullPointerException.class, () ->
                winnerService.getUniqueWinners(null));
    }

    @Test
    void testGetOneTimeWinnersCountNullList() {
        assertThrows(NullPointerException.class, () ->
                winnerService.getOneTimeWinnersCount(null));
    }
}