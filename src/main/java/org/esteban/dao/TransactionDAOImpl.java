package org.esteban.dao;

import org.esteban.exception.ConnectionException;
import org.esteban.exception.TransactionDAOException;
import org.esteban.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TransactionDAOImpl implements TransactionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDAOImpl.class);
    private static final String INSERT_TRANSACTION ="INSERT INTO transaction (id, type, amount, date, account_origin_id, account_destination_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private Connection connection;
    private static final String TYPE = "transaction";

    public TransactionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public TransactionDAOImpl() {
    }

    @Override
    public void createTransaction(String accountOriginId, String accountDestinationId, float amount) throws TransactionDAOException {

        LOGGER.info("Inserting transaction into the database");

        try{
            if (connection == null) {
                connection = DBConnection.getConnection();
                connection.setAutoCommit(false);
            }
        } catch (ConnectionException | SQLException e) {
            throw new TransactionDAOException("Could not connect to database.", e);
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_TRANSACTION)){

            statement.setObject(1, UUID.randomUUID());
            statement.setString(2, TYPE);
            statement.setFloat(3, amount);
            statement.setDate(4,new Date(System.currentTimeMillis()));
            statement.setString(5, accountOriginId);
            statement.setString(6, accountDestinationId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                connection.commit();
                LOGGER.info("Transaction created successfully from {} to {} for amount {}", accountOriginId, accountDestinationId, amount);
            } else {
                connection.rollback();
                throw new TransactionDAOException("Unable to create transaction from " + accountOriginId + " to " + accountDestinationId);
            }

        }catch (SQLException e) {
            throw new TransactionDAOException("Error creating transaction", e);
        }
    }
}
