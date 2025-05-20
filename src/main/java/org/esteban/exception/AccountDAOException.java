package org.esteban.exception;

import java.sql.SQLException;

public class AccountDAOException extends SQLException {
    public AccountDAOException(String message) {
        super(message);
    }

    public AccountDAOException(String message, Throwable cause) {
    super(message, cause);
  }
}
