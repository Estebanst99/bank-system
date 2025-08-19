package org.esteban.dao;

import org.esteban.exception.TransactionDAOException;

public interface TransactionDAO {

    void createTransaction(String accountOriginId, String accountDestinationId, float amount) throws TransactionDAOException;
}
