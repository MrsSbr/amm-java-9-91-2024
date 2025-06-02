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
    private record MasterAdminData(String login, String password) {
    }

    private final MasterAdminData masterAdminData;
    private static final Logger logger = Logger.getLogger(MasterAdminInitializer.class.getName());
    private final DataSource dataSource;

    public MasterAdminInitializer() {
        super();
        dataSource = DatabaseConfiguration.getDataSource();
        masterAdminData = getMasterAdminData();
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

    private MasterAdminData getMasterAdminData() {
        var prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("master_admin.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new MasterAdminData(prop.getProperty("master_admin.login"), prop.getProperty("master_admin.password"));
    }

    private boolean isMasterAdminExists() throws SQLException {
        final String query = """
                SELECT 1
                FROM employee
                WHERE login = ?""";

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, masterAdminData.login);
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
        preparedStatement.setString(1, masterAdminData.login);
        preparedStatement.setString(2, BCrypt.hashpw(masterAdminData.password, BCrypt.gensalt()));
        preparedStatement.setString(3, EmployeePost.MASTER_ADMINISTRATOR.toString());
        preparedStatement.execute();
    }
}
