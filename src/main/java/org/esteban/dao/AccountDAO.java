package org.esteban.dao;

import org.esteban.exception.AccountDAOException;
import org.esteban.model.Account;

public interface AccountDAO {

    void createAccount(String id, String clientId, float balance, String type) throws AccountDAOException;

    void deposit(String accountId, float amount) throws AccountDAOException;

    void withdraw(String accountId, float amount) throws AccountDAOException;

    Account getAccountDetails(String accountId) throws AccountDAOException;
}
