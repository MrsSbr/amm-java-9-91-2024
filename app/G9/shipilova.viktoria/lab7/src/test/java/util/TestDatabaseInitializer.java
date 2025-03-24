package util;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabaseInitializer {

    private final DataSource dataSource;

    public TestDatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeScript(String scriptPath) throws IOException, SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {

            StringBuilder script = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
                if (line.trim().endsWith(";")) {
                    stmt.execute(script.toString());
                    script.setLength(0);
                }
            }
        }
    }

    public void initializeDatabase(String createScriptPath, String insertScriptPath) throws IOException, SQLException {
        executeScript(createScriptPath);
        executeScript(insertScriptPath);
    }
}
