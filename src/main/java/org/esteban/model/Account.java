package org.esteban.model;

public abstract class Account {

    private String id;
    private float balance;
    private String clientId;
    private String accountType;

    public enum AccountType {
        SAVINGS,
        CHECKING
    }

    protected Account(String id, float balance, String clientId, String accountType) {
        this.id = id;
        this.balance = balance;
        this.clientId = clientId;
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
