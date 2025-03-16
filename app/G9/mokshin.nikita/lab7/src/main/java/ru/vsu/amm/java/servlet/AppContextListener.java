package ru.vsu.amm.java.servlet;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.repository.FilmRepository;
import ru.vsu.amm.java.repository.GenreRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;

@WebListener
public class AppContextListener implements ServletContextListener {
    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dataSource = DatabaseConfig.getDataSource();
        UserRepository userRepository = new UserRepository(dataSource);

        sce.getServletContext().setAttribute("dataSource", dataSource);
        sce.getServletContext().setAttribute("userRepository", userRepository);
        sce.getServletContext().setAttribute("genreRepository", new GenreRepository(dataSource));
        sce.getServletContext().setAttribute("filmRepository", new FilmRepository(dataSource));
        sce.getServletContext().setAttribute("authService", new AuthService(userRepository));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
