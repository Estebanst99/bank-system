package org.esteban.service;

import org.esteban.dao.TransactionDAO;
import org.esteban.exception.TransactionDAOException;
import org.esteban.exception.TransactionServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionServiceImpl implements TransactionService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final TransactionDAO transactionDao;

    public TransactionServiceImpl(TransactionDAO transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public void createTransaction(String accountOriginId, String accountDestinationId, float amount) throws TransactionServiceException {
       LOGGER.info("Creating transaction");

       checkParameters(accountOriginId, accountDestinationId, amount);
       try{
           transactionDao.createTransaction(accountOriginId, accountDestinationId, amount);
       }catch (TransactionDAOException e) {
              throw new TransactionServiceException("Error creating transaction");
       }
    }

    private void checkParameters(String accountOriginId, String accountDestinationId, float amount) throws TransactionServiceException {

        if (accountOriginId == null || accountOriginId.isEmpty()) {
            throw new TransactionServiceException("Account origin ID cannot be null or empty");
        }
        if (accountDestinationId == null || accountDestinationId.isEmpty()) {
            throw new TransactionServiceException("Account destination ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new TransactionServiceException("Amount must be greater than zero");
        }

        LOGGER.info("Parameters validated successfully: Origin ID: {}, Destination ID: {}, Amount: {}",
                    accountOriginId, accountDestinationId, amount);
    }
}
