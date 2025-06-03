package org.esteban.service;

import org.esteban.dao.AccountDAO;
import org.esteban.dao.AccountDAOImpl;
import org.esteban.exception.AccountDAOException;
import org.esteban.exception.AccountServiceException;

public class AccountServiceImpl implements AccountService{


   private final AccountDAO accountDAO = new AccountDAOImpl();
   private static final String CHECKING = "CHECKING";
    private static final String SAVINGS = "SAVINGS";

    @Override
    public void createAccount(String id, String clientId, String accountType) throws AccountDAOException {

        if(accountType.equalsIgnoreCase(SAVINGS) || accountType.equalsIgnoreCase(CHECKING)) {
            accountDAO.createAccount(id, clientId,0 , accountType);
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }

    }

    @Override
    public void deposit(String accountId, float amount) throws AccountServiceException {
        try{
            if (amount <= 0) {
                throw new AccountServiceException("Deposit amount must be greater than zero");
            }

            accountDAO.deposit(accountId, amount);

        }catch (AccountDAOException e){
            throw new AccountServiceException("Error depositing amount: " + e.getMessage());
        }
    }

    @Override
    public void withdraw(String accountId, float amount) throws AccountServiceException {

        try {
            if (amount <= 0) {
                throw new AccountServiceException("Withdrawal amount must be greater than zero");
            }
            accountDAO.withdraw(accountId, amount);
        } catch (AccountDAOException e) {
            throw new AccountServiceException("Error withdrawing amount: " + e.getMessage());
        }
    }

    @Override
    public void getAccountDetails(String accountId) throws AccountServiceException {
        try {
            accountDAO.getAccountDetails(accountId);
        } catch (AccountDAOException e) {
            throw new AccountServiceException("Error getting account details: " + e.getMessage());
        }
    }
}
