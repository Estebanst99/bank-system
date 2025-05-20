package org.esteban.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

abstract class DatabaseTest {

    protected static final String databaseUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    protected static final String username = "sa";
    protected static final String password = "";
    protected static final String DROP_SCHEMA = "DROP ALL OBJECTS";

    protected static Connection connection;

    public static void init() throws Exception {

        connection = DriverManager.getConnection(databaseUrl, username, password);
        String createSchema = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(DatabaseTest.class.getResourceAsStream("/schema-definition.sql"))))
                .lines().collect(Collectors.joining("\n"));

        try(Statement statement = connection.createStatement()) {
           statement.execute(createSchema);
        }catch (SQLException e){
            throw new Exception(e.getMessage());
        }
    }

    //TODO: Add catch for SQLException to handle errors
    public static void shutDown() throws Exception{

        if (connection != null && !connection.isClosed()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute(DROP_SCHEMA);
            }catch (SQLException e){
                throw new Exception(e.getMessage());
            }
            connection.close();
        }
    }

}
