package com.oguzhanozgokce.lab6_0505_marmara.domain;

import java.util.List;

public interface TransactionRepository {
    void addTransaction(Transaction transaction);
    void deleteTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
}