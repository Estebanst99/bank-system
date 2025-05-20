package org.esteban.exception;

import java.sql.SQLException;

public class ClientDAOException extends SQLException {

    public ClientDAOException(String message) {
        super(message);
    }

    public ClientDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
