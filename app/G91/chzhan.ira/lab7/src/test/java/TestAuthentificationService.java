import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.exception.AlreadyExistsException;
import ru.vsu.amm.java.service.AuthenticationService;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAuthentificationService {

    @Test
    public void testLoginSuccess() {
        AuthenticationService service = new AuthenticationService();
        Optional<Customer> customer = service.login("admin", "0000");
        if(customer.isEmpty()) {
            service.register("admin", "0000");
            customer = service.login("admin", "0000");
        }
        assertTrue(customer.isPresent());
    }

    @Test
    public void testLoginFailWrongPassword() {
        AuthenticationService service = new AuthenticationService();
        Optional<Customer> customer = service.login("admin", "wrongpassword");
        assertFalse(customer.isPresent());
    }

    @Test
    public void testLoginFailWrongUsername() {
        AuthenticationService service = new AuthenticationService();
        Optional<Customer> customer = service.login("wrongusername", "0000");
        assertFalse(customer.isPresent());
    }

    @Test
    public void testRegisterExistingUser() {
        AuthenticationService service = new AuthenticationService();
        assertThrows(AlreadyExistsException.class, () -> service.register("admin", "0000"));
    }

}
