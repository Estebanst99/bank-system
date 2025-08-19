package org.esteban.exception;

import java.sql.SQLException;

public class TransactionDAOException extends SQLException {
    public TransactionDAOException(String message) {
        super(message);
    }

    public TransactionDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
