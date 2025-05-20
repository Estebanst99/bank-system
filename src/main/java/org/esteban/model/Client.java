package org.esteban.model;

import java.util.List;

public class Client {

    private String id;
    private String name;
    private List<Account> accounts;

    public Client(String id, String name, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.accounts = accounts;
    }

    public Client() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
