import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers() {
        UserEntity user1 = new UserEntity("test1", "test", 1000d, List.of(Role.Player));
        UserEntity user2 = new UserEntity("test2", "test", 1000d, List.of(Role.Player));

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserEntity> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("test1", users.getFirst().getNickname());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserByNickname() {
        UserEntity user = new UserEntity("test", "test", 1000d, List.of(Role.Player));

        when(userRepository.findByNickname("test")).thenReturn(user);

        UserEntity found = userService.getUserByNickname("test");

        assertNotNull(found);
        assertEquals("test", found.getNickname());
        verify(userRepository).findByNickname("test");
    }

    @Test
    void testGetUserByNicknameNotFound() {
        when(userRepository.findByNickname("test")).thenReturn(null);

        assertNull(userService.getUserByNickname("test"));
    }
}