package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.SelectUserEntity;
import ru.vsu.amm.java.entities.UpdateUserEntity;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.services.UsersSQLQueriesGenerator;

import java.time.LocalDate;

public class UsersSQLQueriesGeneratorTests {
    private final int USER_ID = 42;

    @Test
    public void createUserTest() {
        var userEntity = new UserEntity(USER_ID, "BimBamBum", "bururi", LocalDate.now());
        var insertSQLQuery = String.format(
                "INSERT INTO users_table (id, userName, password, creationTime) VALUES (%s, '%s', '%s', '%s');",
                userEntity.id(), userEntity.userName(), userEntity.password(), userEntity.creationTime());
        Assertions.assertEquals(insertSQLQuery, UsersSQLQueriesGenerator.createUser(userEntity));
    }

    @Test
    public void getUsersTest() {
        var selectUserEntity = new SelectUserEntity(true, true, true, true);
        var selectSQLQuery = "SELECT id, userName, password, creationTime FROM users_table;";
        Assertions.assertEquals(selectSQLQuery, UsersSQLQueriesGenerator.getUsers(selectUserEntity));

        selectUserEntity = new SelectUserEntity(false, false, false, false);
        selectSQLQuery = "SELECT * FROM users_table;";
        Assertions.assertEquals(selectSQLQuery, UsersSQLQueriesGenerator.getUsers(selectUserEntity));

        selectUserEntity = new SelectUserEntity(true, true, false, false);
        selectSQLQuery = "SELECT id, userName FROM users_table;";
        Assertions.assertEquals(selectSQLQuery, UsersSQLQueriesGenerator.getUsers(selectUserEntity));
    }

    @Test
    public void updateUserTest() {
        var creationTime = LocalDate.now().plusDays(31);
        var updateUserEntity = new UpdateUserEntity("John", null, creationTime);
        var updateSQLQuery = String.format("UPDATE users_table SET userName = '%s', creationTime = '%s' WHERE id = %d;",
                updateUserEntity.userName(), creationTime, USER_ID);
        Assertions.assertEquals(updateSQLQuery, UsersSQLQueriesGenerator.updateUser(USER_ID, updateUserEntity));
    }

    @Test
    public void deleteUsersTest() {
        var deleteSQLQuery = "DELETE FROM users_table;";
        Assertions.assertEquals(deleteSQLQuery, UsersSQLQueriesGenerator.deleteUsers());
    }
}
