package org.esteban.dao;

import org.esteban.exception.ClientDAOException;
import org.esteban.model.Client;
import org.esteban.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDAOImpl implements ClientDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDAOImpl.class);
    private static final String CREATE_CLIENT_SQL = "INSERT INTO client (id, name) VALUES (?, ?)";
    private static final String SELECT_CLIENT = "SELECT * FROM client WHERE id = ?";
    private Connection connection;

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public ClientDAOImpl() {
    }

    @Override
    public void createClient(String id, String name) throws ClientDAOException {

        try {
            if (connection == null) {
                connection = DBConnection.getConnection();
                connection.setAutoCommit(false);
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_SQL)) {
                LOGGER.debug("Creating client with ID: {} and Name: {}", id, name);
                connection.setAutoCommit(false);
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, name);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 1) {
                    connection.commit();
                    LOGGER.info("Client created successfully with ID: {}", id);
                } else {
                    connection.rollback();
                    throw new ClientDAOException("Unable to create client with ID: " + id);
                }
            }
        } catch (Exception e) {
            throw new ClientDAOException(e.getMessage());
        }
    }

    @Override
    public Client getClient(String id) throws ClientDAOException {
        try {
           if (connection == null) {
                connection = DBConnection.getConnection();
                connection.setAutoCommit(false);
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT)) {
                LOGGER.debug("Getting client with ID: {}", id);

                preparedStatement.setString(1, id);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    Client client = new Client();
                    String clientId = rs.getString("id");
                    String clientName = rs.getString("name");
                    client.setId(clientId);
                    client.setName(clientName);
                    LOGGER.info("Client details: ID: {}, Name: {}", clientId, clientName);
                    return client;
                } else {
                    LOGGER.warn("Client with ID: {} not found", id);
                    return null;
                }
            }
        } catch (Exception e) {
            throw new ClientDAOException(e.getMessage());
        }
    }
}
