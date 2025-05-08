package ru.vsu.amm.java.initializer;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.enums.EmployeePost;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

@WebListener
public class MasterAdminInitializer implements ServletContextListener {
    private final String MASTER_ADMIN_LOGIN;
    private final String MASTER_ADMIN_PASSWORD;
    private static final Logger logger = Logger.getLogger(MasterAdminInitializer.class.getName());
    private final DataSource dataSource;

    public MasterAdminInitializer() {
        super();
        dataSource = DatabaseConfiguration.getDataSource();
        var masterAdminData = getMasterAdminData();
        MASTER_ADMIN_LOGIN = masterAdminData[0];
        MASTER_ADMIN_PASSWORD = masterAdminData[1];
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            if (!isMasterAdminExists()) {
                createMasterAdmin();
                logger.info("Master admin was created");
            } else {
                logger.info("Master admin already exists");
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    private String[] getMasterAdminData() {
        var prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("master_admin.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        var data = new String[2];
        data[0] = prop.getProperty("master_admin.login");
        data[1] = prop.getProperty("master_admin.password");
        return data;
    }

    private boolean isMasterAdminExists() throws SQLException {
        final String query = """
                    SELECT 1
                    FROM employee
                    WHERE login = ?""";

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, MASTER_ADMIN_LOGIN);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        return resultSet.next();
    }

    private void createMasterAdmin() throws SQLException {
        final String query = """
                    INSERT INTO employee (login, password, post)
                    VALUES(?, ?, ?)""";

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, MASTER_ADMIN_LOGIN);
        preparedStatement.setString(2, BCrypt.hashpw(MASTER_ADMIN_PASSWORD, BCrypt.gensalt()));
        preparedStatement.setString(3, EmployeePost.MASTER_ADMINISTRATOR.toString());
        preparedStatement.execute();
    }
}
