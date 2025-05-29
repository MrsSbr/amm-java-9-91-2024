package ru.vsu.amm.java.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.Exeption.NotFoundException;
import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Repository.ShareholderRepository;
import ru.vsu.amm.java.Service.Entities.ShareholderCreateModel;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceUnitTest {

    private ShareholderRepository shareholderRepository;
    private UserService userService;
    @BeforeEach
    void setUp() {
        shareholderRepository = mock(ShareholderRepository.class);
        userService = new UserService(shareholderRepository);
    }

    @Test
    void registerPositiveResult() throws SQLException {
        ShareholderCreateModel shareholder = new ShareholderCreateModel("1","2","3","1");
        String password = "Password";
        boolean res = userService.register(shareholder,password);
        assertTrue(res);
    }

    @Test
    void registerNegativeResult() throws SQLException {
        ShareholderCreateModel createModel = new ShareholderCreateModel("1","2","3","1");
        Shareholder shareholder = new Shareholder(1,"1234qwer","1","1","3","1");
        String password = "Password";
        when(shareholderRepository.getByEmail("3")).thenReturn(Optional.of(shareholder));
        boolean res = userService.register(createModel,password);
        assertFalse(res);
    }

    @Test
    void loginPositiveResult() throws SQLException {
        String email = "3";
        String password = "Password";
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        Shareholder shareholder = new Shareholder(1,passwordHash,"1","1",email,"1");
        when(shareholderRepository.getByEmail("3")).thenReturn(Optional.of(shareholder));
        Shareholder res = userService.login(email,password);
        assertEquals(res,shareholder);
    }

    @Test
    void loginNotFoundResult() throws SQLException {
        String email = "3";
        String password = "Password";
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> userService.login(email,password)
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void loginUnCorrectDataResult() throws SQLException {
        String email = "3";
        String password = "Password";
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        Shareholder shareholder = new Shareholder(1,passwordHash,"1","1",email,"1");
        when(shareholderRepository.getByEmail("3")).thenReturn(Optional.of(shareholder));
        UnCorrectDataException exception = assertThrows(
                UnCorrectDataException.class,
                () -> userService.login(email,"vzlom")
        );
        assertEquals("Wrong password", exception.getMessage());
    }


}