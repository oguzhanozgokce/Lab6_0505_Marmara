package com.oguzhanozgokce.lab6_0505_marmara.data.repository;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.oguzhanozgokce.lab6_0505_marmara.data.local.TransactionDao;
import com.oguzhanozgokce.lab6_0505_marmara.data.mapper.TransactionMapper;
import com.oguzhanozgokce.lab6_0505_marmara.domain.Transaction;
import com.oguzhanozgokce.lab6_0505_marmara.domain.TransactionRepository;

@Singleton
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionDao dao;

    @Inject
    public TransactionRepositoryImpl(TransactionDao dao) {
        this.dao = dao;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        var entity = TransactionMapper.toEntity(transaction);
        long newId = dao.insert(entity);
        transaction.setId((int) newId);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        var entity = TransactionMapper.toEntity(transaction);
        dao.delete(entity);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        var entity = TransactionMapper.toEntity(transaction);
        dao.update(entity);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        var entities = dao.getAll();
        return TransactionMapper.toDomainList(entities);
    }
}
