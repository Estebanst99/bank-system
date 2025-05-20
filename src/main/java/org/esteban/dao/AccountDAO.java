package org.esteban.dao;

import org.esteban.exception.AccountDAOException;

public interface AccountDAO {

    void createAccount(String id, String clientId, float balance, String type) throws AccountDAOException;

    void deposit(String accountId, float amount) throws AccountDAOException;

    void withdraw(String accountId, float amount) throws AccountDAOException;
}
