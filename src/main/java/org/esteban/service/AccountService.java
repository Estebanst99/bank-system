package org.esteban.service;

import org.esteban.exception.AccountDAOException;

public interface AccountService {

    void createAccount(String id, String clientId, String accountType) throws AccountDAOException;

    void deposit(String accountId, float amount);

    void withdraw(String accountId, float amount);

    void getAccountDetails(String accountId);

}
