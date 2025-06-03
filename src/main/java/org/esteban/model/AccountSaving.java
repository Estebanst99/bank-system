package org.esteban.model;

public class AccountSaving extends Account{
    /*private float interestRate;*/

    public AccountSaving(String id, float balance, String clientId, String accountType/*, float interestRate*/) {
        super(id, balance, clientId, accountType);
        /*this.interestRate = interestRate;*/
    }

   /* public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }*/

    @Override
    public String toString() {
        return "AccountSaving{" +
                /*"interestRate=" + interestRate +*/
                ", id='" + getId() + '\'' +
                ", balance=" + getBalance() +
                ", clientId='" + getClientId() + '\'' +
                ", accountType='" + getAccountType() + '\'' +
                '}';
    }
}
