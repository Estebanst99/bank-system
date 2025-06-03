package org.esteban.service;

import org.esteban.exception.AccountDAOException;
import org.esteban.exception.AccountServiceException;

public interface AccountService {

    void createAccount(String id, String clientId, String accountType) throws AccountDAOException;

    void deposit(String accountId, float amount) throws AccountServiceException;

    void withdraw(String accountId, float amount) throws AccountServiceException;

    void getAccountDetails(String accountId) throws AccountServiceException;

}
