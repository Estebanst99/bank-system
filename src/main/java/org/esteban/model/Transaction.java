package org.esteban.model;

public class Transaction {

    private String id;
    private String accountOriginId;
    private String accountDestinationId;
    private float amount;

    public Transaction(String id, String accountOriginId, String accountDestinationId, float amount) {
        this.id = id;
        this.accountOriginId = accountOriginId;
        this.accountDestinationId = accountDestinationId;
        this.amount = amount;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountOriginId() {
        return accountOriginId;
    }

    public void setAccountOriginId(String accountOriginId) {
        this.accountOriginId = accountOriginId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAccountDestinationId() {
        return accountDestinationId;
    }

    public void setAccountDestinationId(String accountDestinationId) {
        this.accountDestinationId = accountDestinationId;
    }
}
