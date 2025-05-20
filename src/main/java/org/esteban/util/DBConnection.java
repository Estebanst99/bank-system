package org.esteban.util;

import org.esteban.exception.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);
    private static Connection connection = null;


    public static Connection getConnection() throws ConnectionException {

        if (connection != null) {
            return connection;
        }
        try {
            LOGGER.debug("Connecting to database...");
            String url = System.getProperty("db.url");
            String user = System.getProperty("db.user");
            String password = System.getProperty("db.password");

            if (url == null || user == null || password == null) {
                LOGGER.error("Database connection properties are not set. Please set db.url, db.user, and db.password system properties.");
                throw new ConnectionException("Database connection properties are not set.");
            }

            connection = DriverManager.getConnection(url, user, password);
            return connection;

        } catch (Exception e) {
            throw new ConnectionException("Could not connect to database.", e);
        }
    }
}
