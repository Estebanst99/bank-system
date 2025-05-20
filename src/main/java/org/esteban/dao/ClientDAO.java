package org.esteban.dao;

import org.esteban.exception.ClientDAOException;
import org.esteban.model.Client;

public interface ClientDAO {

    void createClient(String id, String name) throws ClientDAOException;

    Client getClient(String id) throws ClientDAOException;
}
