package util;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class TestDataSourceProvider {
    private static DataSource testDataSource;

    public static DataSource getTestDataSource() {
        if (testDataSource == null) {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
            ds.setUser("sa");
            ds.setPassword("");
            testDataSource = ds;
        }
        return testDataSource;
    }
}
