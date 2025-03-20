import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.servlet.LoginServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginServletTest {

    private LoginServlet loginServlet;
    private UserRepository userRepository;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        loginServlet = new LoginServlet();
        userRepository = mock(UserRepository.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    void testLoginSuccess() throws IOException, ServletException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userRepository.getByEmail("test@example.com")).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        loginServlet.doPost(request, response);

        verify(session).setAttribute(eq("user"), any(User.class));
        verify(response).sendRedirect("dashboard.jsp");
    }
}
