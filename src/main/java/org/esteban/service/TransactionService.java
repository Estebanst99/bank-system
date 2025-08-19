package org.esteban.service;

import org.esteban.exception.TransactionServiceException;

public interface TransactionService {

    void createTransaction (String accountOriginId, String accountDestinationId, float amount) throws TransactionServiceException, TransactionServiceException;

}
