package dbConfigurator;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class DatabaseHelper {
    private final static String SCRIPTS_CREATE_TABLES_SQL = "db/scripts/create_tables_query.sql";
    private final static String SCRIPTS_DELETE_FROM_TABLES_SQL = "db/scripts/delete_from_tables_query.sql";
    private final static String SCRIPTS_DROP_TABLES_SQL = "db/scripts/drop_tables_query.sql";

    public static void createTables() throws SQLException, IOException {
        executeSql(SCRIPTS_CREATE_TABLES_SQL);
    }

    public static void truncateTables() throws SQLException, IOException {
        executeSql(SCRIPTS_DELETE_FROM_TABLES_SQL);
    }

    public static void dropTables() throws SQLException, IOException {
        executeSql(SCRIPTS_DROP_TABLES_SQL);
    }

    private static void executeSql(String sqlScriptPath) throws SQLException, IOException {
        var dataSource = DatabaseConfiguration.getDataSource();
        try (var connection = dataSource.getConnection();
             var stmt = connection.createStatement()) {
            stmt.executeUpdate(readSql(sqlScriptPath));
        }
    }

    private static String readSql(String scriptPath) throws IOException {
        try (var inputStream = DatabaseHelper.class.getClassLoader().getResourceAsStream(scriptPath)) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
