package org.esteban.dao;

import org.esteban.exception.AccountDAOException;
import org.esteban.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAOImpl implements AccountDAO {

    private static final  Logger LOGGER = LoggerFactory.getLogger(AccountDAOImpl.class);
    private Connection connection;

    private static final String CREATE_ACCOUNT = "INSERT INTO account (id, client_id, balance, type) VALUES (?, ?, ?, ?)";
    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String UPDATE_BALANCE = "UPDATE account SET balance = balance + ? WHERE id = ?";
    public static final String COULD_NOT_CONNECT_TO_DATABASE = "Could not connect to database.";

    public AccountDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public AccountDAOImpl() {

    }

    @Override
    public void createAccount(String id, String clientId, float balance, String type) throws AccountDAOException {

        try {
            if (connection == null) {
                connection = DBConnection.getConnection();
                connection.setAutoCommit(false);
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ACCOUNT)) {
                LOGGER.debug("Creating account with id {}", id);
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, clientId);
                preparedStatement.setFloat(3, balance);
                preparedStatement.setString(4, type);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 1) {
                    connection.commit();
                    LOGGER.info("Account created successfully with id {}", id);
                } else {
                    connection.rollback();
                    throw new AccountDAOException("Unable to create account with id: " + id);
                }
            }
        } catch (Exception e) {
            throw new AccountDAOException(COULD_NOT_CONNECT_TO_DATABASE, e);
        }
    }

    @Override
    public void deposit(String accountId, float amount) throws AccountDAOException {

        try {
            if (connection == null) {
                connection = DBConnection.getConnection();
                connection.setAutoCommit(false);
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BALANCE)) {
                LOGGER.debug("Depositing {} to account with id {}", amount, accountId);

                if (amount <= 0) {
                    throw new AccountDAOException("Deposit amount must be greater than zero.");
                }

                preparedStatement.setFloat(1, amount);
                preparedStatement.setString(2, accountId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 1) {
                    connection.commit();
                    LOGGER.info("Deposited {} to account with id {}", amount, accountId);
                } else {
                    connection.rollback();
                    throw new AccountDAOException("Unable to deposit to account with id: " + accountId);
                }
            }
        } catch (Exception e) {
            throw new AccountDAOException(COULD_NOT_CONNECT_TO_DATABASE, e);
        }
    }

    @Override
    public void withdraw(String accountId, float amount) throws AccountDAOException {

        try {
            if (connection == null) {
                connection = DBConnection.getConnection();
                connection.setAutoCommit(false);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_BY_ID);
                PreparedStatement preparedStatement2 = connection.prepareStatement(UPDATE_BALANCE)) {

                LOGGER.debug("Withdrawing {} to account with id {}", amount, accountId);
                preparedStatement.setString(1, accountId);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    float currentBalance = rs.getFloat("balance");
                    if (currentBalance >= amount) {
                        preparedStatement2.setFloat(1, -amount);
                        preparedStatement2.setString(2, accountId);
                        int rowsAffected = preparedStatement2.executeUpdate();

                        if (rowsAffected == 1) {
                            connection.commit();
                            LOGGER.info("Withdrew {} from account with id {}", amount, accountId);
                        } else {
                            connection.rollback();
                            throw new AccountDAOException("Unable to withdraw from account with id: " + accountId);
                        }
                    } else {
                        connection.rollback();
                        throw new AccountDAOException("Insufficient funds in account with id: " + accountId);
                    }
                }else {
                    connection.rollback();
                    throw new AccountDAOException("Account with id: " + accountId + " not found.");
                }
            }
        } catch (Exception e) {
            throw new AccountDAOException(COULD_NOT_CONNECT_TO_DATABASE, e);
        }
    }
}
