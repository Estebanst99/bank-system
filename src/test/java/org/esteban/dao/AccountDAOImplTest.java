package org.esteban.dao;

import org.esteban.exception.AccountDAOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountDAOImplTest extends DatabaseTest{

    private static final String SELECT = "SELECT * FROM account WHERE id = ?";
    private final Connection conn = connection;
    private final AccountDAO accountDAO = new AccountDAOImpl(conn);
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
    void createAccountSuccess() throws SQLException {

        String clientId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String type = "SAVINGS";
        float balance = 0;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        accountDAO.createAccount(id,clientId,balance,type);

        PreparedStatement preparedStatement = conn.prepareStatement(SELECT);
        preparedStatement.setString(1, id);
        var resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            assertEquals(id,resultSet.getString("id"));
            assertEquals(clientId,resultSet.getString("client_id"));
            assertEquals(type,resultSet.getString("type"));
            assertEquals(balance,resultSet.getFloat("balance"));
        }
    }

    @Test
    void createAccountFailure() throws SQLException {
        String clientId = UUID.randomUUID().toString();
        String id = null;
        String type = "SAVINGS";
        float balance = 0;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        assertThrows(AccountDAOException.class, () -> accountDAO.createAccount(id,clientId,balance,type));
    }

    @Test
    void depositSuccess() throws SQLException {
        String clientId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String type = "SAVINGS";
        float balance = 0;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        accountDAO.createAccount(id,clientId,balance,type);

        float depositAmount = 1000;
        accountDAO.deposit(id, depositAmount);

        PreparedStatement preparedStatement = conn.prepareStatement(SELECT);
        preparedStatement.setString(1, id);
        var resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            assertEquals(id,resultSet.getString("id"));
            assertEquals(clientId,resultSet.getString("client_id"));
            assertEquals(type,resultSet.getString("type"));
            assertEquals(depositAmount,resultSet.getFloat("balance"));
        }
    }

    @Test
    void depositFailure() throws SQLException {
        String clientId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String type = "SAVINGS";
        float balance = 0;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        accountDAO.createAccount(id,clientId,balance,type);

        assertThrows(AccountDAOException.class, () -> accountDAO.deposit(id, -100));
    }

    @Test
    void depositFailureAccountNotFound() throws SQLException {
        String id = UUID.randomUUID().toString();
        assertThrows(AccountDAOException.class, () -> accountDAO.deposit(id, 100));
    }

    @Test
    void withdrawSuccess() throws SQLException {
        String clientId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String type = "SAVINGS";
        float balance = 1000;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        accountDAO.createAccount(id,clientId,balance,type);

        float withdrawAmount = 500;
        accountDAO.withdraw(id, withdrawAmount);

        PreparedStatement preparedStatement = conn.prepareStatement(SELECT);
        preparedStatement.setString(1, id);
        var resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            assertEquals(id,resultSet.getString("id"));
            assertEquals(clientId,resultSet.getString("client_id"));
            assertEquals(type,resultSet.getString("type"));
            assertEquals(balance - withdrawAmount,resultSet.getFloat("balance"));
        }
    }

    @Test
    void withdrawFailure() throws SQLException {
        String clientId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String type = "SAVINGS";
        float balance = 1000;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        accountDAO.createAccount(id,clientId,balance,type);

        assertThrows(AccountDAOException.class, () -> accountDAO.withdraw(id, 1100));
    }

    @Test
    void withdrawFailureAccountNotFound() throws SQLException {
        String id = UUID.randomUUID().toString();
        assertThrows(AccountDAOException.class, () -> accountDAO.withdraw(id, 100));
    }

    @Test
    void getAccountDetailsSuccess() throws SQLException {
        String clientId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String type = "SAVINGS";
        float balance = 1000;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        accountDAO.createAccount(id,clientId,balance,type);

        var account = accountDAO.getAccountDetails(id);

        assertEquals(id,account.getId());
        assertEquals(clientId,account.getClientId());
        assertEquals(type,account.getAccountType());
        assertEquals(balance,account.getBalance());
    }

    @Test
    void getAccountCheckingDetailsSuccess() throws SQLException {
        String clientId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String type = "CHECKING";
        float balance = 1000;

        // Create a client first to match with the foreign key constraint
        String clientName = "Esteban";
        clientDAO.createClient(clientId, clientName);

        accountDAO.createAccount(id,clientId,balance,type);

        var account = accountDAO.getAccountDetails(id);

        assertEquals(id,account.getId());
        assertEquals(clientId,account.getClientId());
        assertEquals(type,account.getAccountType());
        assertEquals(balance,account.getBalance());
    }

    @Test
    void getAccountDetailsFailure() throws SQLException {
        String id = UUID.randomUUID().toString();
        assertThrows(AccountDAOException.class, () -> accountDAO.getAccountDetails(id));
    }
}