import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.DoctorAppointment;
import ru.vsu.amm.java.DoctorAppointmentManager;
import ru.vsu.amm.java.Specialization;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorAppointmentManagerTest {

    private List<DoctorAppointment> createTestAppointments() {
        return List.of(
                new DoctorAppointment(LocalDate.of(2024, 3, 5), "Dr. Smith", Specialization.CARDIOLOGIST, "John Doe", 150),
                new DoctorAppointment(LocalDate.of(2024, 7, 14), "Dr. Smith", Specialization.CARDIOLOGIST, "Jane Roe", 200),
                new DoctorAppointment(LocalDate.of(2025, 2, 14), "Dr. Smith", Specialization.CARDIOLOGIST, "Jane Roe", 250),
                new DoctorAppointment(LocalDate.of(2024, 11, 20), "Dr. Jones", Specialization.SURGEON, "Alice Green", 250),
                new DoctorAppointment(LocalDate.of(2023, 5, 15), "Dr. Smith", Specialization.CARDIOLOGIST, "John Doe", 120),
                new DoctorAppointment(LocalDate.of(2024, 7, 10), "Dr. Jones", Specialization.SURGEON, "Bob Black", 300),
                new DoctorAppointment(LocalDate.of(2020, 7, 10), "Dr. Jones", Specialization.SURGEON, "John Doe", 200),
                new DoctorAppointment(LocalDate.of(2024, 10, 5), "Dr. Brown", Specialization.PEDIATRICIAN, "Charlie White", 180),
                new DoctorAppointment(LocalDate.of(2025, 1, 5), "Dr. Brown", Specialization.PEDIATRICIAN, "Charlie White", 200),
                new DoctorAppointment(LocalDate.of(2022, 10, 5), "Dr. Brown", Specialization.PEDIATRICIAN, "John Doe", 150)

        );
    }

    @Test
    public void testGetIncomeBySpecialization() {
        List<DoctorAppointment> appointments = createTestAppointments();

        int cardiologistIncome = DoctorAppointmentManager.getIncomeBySpecialization(appointments, Specialization.CARDIOLOGIST);
        assertEquals(450, cardiologistIncome);

        int surgeonIncome = DoctorAppointmentManager.getIncomeBySpecialization(appointments, Specialization.SURGEON);
        assertEquals(550, surgeonIncome);

        int pediatricianIncome = DoctorAppointmentManager.getIncomeBySpecialization(appointments, Specialization.PEDIATRICIAN);
        assertEquals(380, pediatricianIncome);
    }

    @Test
    public void testGetPatientsForAllDoctors() {
        List<DoctorAppointment> appointments = createTestAppointments();

        Set<String> commonPatients = DoctorAppointmentManager.getPatientsForAllDoctors(appointments);
        Set<String> expectedPatients = new HashSet<>(List.of("John Doe"));

        assertEquals(expectedPatients, commonPatients);
    }

    @Test
    public void testGetPatientsLastYearNotThisYear() {
        List<DoctorAppointment> appointments = createTestAppointments();

        Map<String, Set<String>> patientsLastYearNotThisYear = DoctorAppointmentManager.getPatientsLastYearNotThisYear(appointments);

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("Dr. Smith", new HashSet<>(Collections.singletonList("John Doe")));
        expected.put("Dr. Jones", new HashSet<>(List.of("Bob Black", "Alice Green")));
        expected.put("Dr. Brown", new HashSet<>());

        assertEquals(expected, patientsLastYearNotThisYear);
    }
}
