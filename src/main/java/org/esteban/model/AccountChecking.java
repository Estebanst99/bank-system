package org.esteban.model;

public class AccountChecking extends Account{

    /*private float overdraftLimit;*/

    public AccountChecking(String id, float balance, String clientId, String accountType/*, float overdraftLimit*/) {
        super(id, balance, clientId, accountType);
        /*this.overdraftLimit = overdraftLimit;*/
    }

    /*public float getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(float overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }*/

    @Override
    public String toString() {
        return "AccountChecking{" +
                /*"overdraftLimit=" + overdraftLimit +*/
                ", id='" + getId() + '\'' +
                ", balance=" + getBalance() +
                ", clientId='" + getClientId() + '\'' +
                ", accountType='" + getAccountType() + '\'' +
                '}';
    }
}
