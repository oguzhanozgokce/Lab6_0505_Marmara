package com.oguzhanozgokce.lab6_0505_marmara.domain;

public class Transaction {
    private int id;
    private String personName;
    private String transactionType;
    private double amount;
    private String date;

    public Transaction(int id, String personName, String transactionType, double amount, String date) {
        this.id = id;
        this.personName = personName;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(String personName, String transactionType, double amount, String date) {
        this.personName = personName;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }
}
