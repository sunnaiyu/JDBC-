package com.jsoft.afternoon.test;

public class Account {

    private Integer id;
    private String accountid;
    private Double balance;


    public Account() {
    }

    public Account(Integer id, String accountid, Double balance) {
        this.id = id;
        this.accountid = accountid;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
