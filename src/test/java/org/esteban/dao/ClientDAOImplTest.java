package org.esteban.dao;

import org.esteban.exception.ClientDAOException;
import org.esteban.model.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientDAOImplTest extends DatabaseTest {

    private static final String SELECT = "SELECT * FROM client WHERE id = ?";
    private final Connection conn = connection;
    private final ClientDAO clientDAO = new ClientDAOImpl(conn);

    @BeforeAll
    static void setUp() throws Exception {
        init();
    }

    @AfterAll
    static void tearDown() throws Exception {
        shutDown();
    }

    @Test
    void createClientSuccess() throws SQLException {
        String name = "Esteban";
        String id = UUID.randomUUID().toString();

        clientDAO.createClient(id, name);
        Client client = clientDAO.getClient(id);

        assertEquals(id,client.getId());
        assertEquals(name,client.getName());
    }

    @Test
    void createClientRollback() throws SQLException {

       assertThrows(ClientDAOException.class, () -> clientDAO.createClient(null,null ));
    }


    @Test
    void getClientNotFound() throws SQLException {
        String id = UUID.randomUUID().toString();
        Client client = clientDAO.getClient(id);
        assertNull(client);
    }

    @Test
    void createClientThrowsException() throws SQLException {
        String name = "Esteban";
        String name2 = "Esteban2";
        String id = UUID.randomUUID().toString();

        clientDAO.createClient(id, name);

        assertThrows(ClientDAOException.class, () -> clientDAO.createClient(id, name2));
    }

    @Test
    void getClientThrowsException() throws SQLException {
        String name = "Esteban";
        String name2 = "Esteban2";
        String id = UUID.randomUUID().toString();

        clientDAO.createClient(id, name);

        assertThrows(ClientDAOException.class, () -> clientDAO.getClient(name2));

    }
}