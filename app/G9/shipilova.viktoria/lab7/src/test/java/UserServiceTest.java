import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserByLoginAndPassword_UserExists() {
        String login = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        when(userRepository.findByLoginAndPassword(login, password)).thenReturn(user);

        User result = userService.findUserByLoginAndPassword(login, password);

        assertNotNull(result);
        assertEquals(login, result.getLogin());
        assertEquals(password, result.getPassword());

        verify(userRepository, times(1)).findByLoginAndPassword(login, password);
    }

    @Test
    public void testFindUserByLoginAndPassword_UserNotExists() {
        String login = "nonexistentUser";
        String password = "wrongPassword";
        when(userRepository.findByLoginAndPassword(login, password)).thenReturn(null);

        User result = userService.findUserByLoginAndPassword(login, password);

        assertNull(result);

        verify(userRepository, times(1)).findByLoginAndPassword(login, password);
    }

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword("newPassword");

        userService.registerUser(user);

        verify(userRepository, times(1)).save(user);
    }
}
