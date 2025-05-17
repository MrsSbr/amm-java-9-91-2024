import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.exception.AlreadyExistsException;
import ru.vsu.amm.java.repository.CustomerRepository;
import ru.vsu.amm.java.service.AuthenticationService;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestAuthentificationService {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private final String EXISTING_USERNAME = "admin";
    private final String CORRECT_PASSWORD = "0000";
    private final String WRONG_PASSWORD = "wrongpassword";
    private final String NON_EXISTENT_USERNAME = "wrongusername";

    @BeforeEach
    void setUp() throws Exception {
        authenticationService = new AuthenticationService();
        Field field = AuthenticationService.class.getDeclaredField("customerRepository");
        field.setAccessible(true);
        field.set(authenticationService, customerRepository);
    }

    @Test
    void testLoginSuccess() throws SQLException {
        String hashedPassword = BCrypt.hashpw(CORRECT_PASSWORD, BCrypt.gensalt());
        Customer mockCustomer = new Customer(1L, EXISTING_USERNAME, hashedPassword);

        when(customerRepository.findByName(EXISTING_USERNAME)).thenReturn(Optional.of(mockCustomer));

        Optional<Customer> result = authenticationService.login(EXISTING_USERNAME, CORRECT_PASSWORD);

        assertTrue(result.isPresent());
        assertEquals(EXISTING_USERNAME, result.get().getName());
        verify(customerRepository, times(1)).findByName(EXISTING_USERNAME);
    }

    @Test
    void testLoginFailWrongPassword() throws SQLException {
        String hashedPassword = BCrypt.hashpw(CORRECT_PASSWORD, BCrypt.gensalt());
        Customer mockCustomer = new Customer(1L, EXISTING_USERNAME, hashedPassword);

        when(customerRepository.findByName(EXISTING_USERNAME)).thenReturn(Optional.of(mockCustomer));

        Optional<Customer> result = authenticationService.login(EXISTING_USERNAME, WRONG_PASSWORD);

        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findByName(EXISTING_USERNAME);
    }

    @Test
    void testLoginFailWrongUsername() throws SQLException {
        when(customerRepository.findByName(NON_EXISTENT_USERNAME)).thenReturn(Optional.empty());

        Optional<Customer> result = authenticationService.login(NON_EXISTENT_USERNAME, CORRECT_PASSWORD);

        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findByName(NON_EXISTENT_USERNAME);
    }

    @Test
    void testRegisterNewUserSuccess() throws SQLException {

        when(customerRepository.findByName(NON_EXISTENT_USERNAME)).thenReturn(Optional.empty());
        doNothing().when(customerRepository).save(any(Customer.class));

        assertDoesNotThrow(() -> authenticationService.register(NON_EXISTENT_USERNAME, CORRECT_PASSWORD));
        verify(customerRepository, times(1)).findByName(NON_EXISTENT_USERNAME);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testRegisterExistingUser() throws SQLException {
        String hashedPassword = BCrypt.hashpw(CORRECT_PASSWORD, BCrypt.gensalt());
        Customer existingCustomer = new Customer(1L, EXISTING_USERNAME, hashedPassword);

        when(customerRepository.findByName(EXISTING_USERNAME)).thenReturn(Optional.of(existingCustomer));

        assertThrows(AlreadyExistsException.class, () ->
                authenticationService.register(EXISTING_USERNAME, CORRECT_PASSWORD));
        verify(customerRepository, times(1)).findByName(EXISTING_USERNAME);
        verify(customerRepository, never()).save(any(Customer.class));
    }
}