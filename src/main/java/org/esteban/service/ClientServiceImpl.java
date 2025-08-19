package org.esteban.service;

import org.esteban.dao.ClientDAO;
import org.esteban.exception.ClientDAOException;
import org.esteban.exception.ClientServiceException;

public class ClientServiceImpl implements ClientService {

    /*private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);*/
    private ClientDAO clientDAO;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public void createClient(String id, String name) {
        try {
            clientDAO.createClient(id, name);
        }catch (ClientDAOException e ){
            throw new ClientServiceException("Error creating client: " + e.getMessage());
        }
    }

    @Override
    public void getClient(String id) {
        try {
            clientDAO.getClient(id);
        } catch (ClientDAOException e) {
            throw new ClientServiceException("Error getting client: " + e.getMessage());
        }
    }
}
