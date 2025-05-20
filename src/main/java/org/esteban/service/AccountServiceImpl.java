package org.esteban.service;

import org.esteban.dao.AccountDAO;
import org.esteban.dao.AccountDAOImpl;
import org.esteban.exception.AccountDAOException;

public class AccountServiceImpl implements AccountService{


   private final AccountDAO accountDAO = new AccountDAOImpl();
   private static final String CHECKING = "CHECKING";
    private static final String SAVINGS = "SAVINGS";

    @Override
    public void createAccount(String id, String clientId, String accountType) throws AccountDAOException {

        if(accountType.equalsIgnoreCase(SAVINGS) || accountType.equalsIgnoreCase(CHECKING)) {
            // Call the DAO to create the account
            accountDAO.createAccount(id, clientId,0 , accountType);
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }

    }

    @Override
    public void deposit(String accountId, float amount) {

    }

    @Override
    public void withdraw(String accountId, float amount) {

    }

    @Override
    public void getAccountDetails(String accountId) {

    }
}
