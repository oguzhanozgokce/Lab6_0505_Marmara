package com.oguzhanozgokce.lab6_0505_marmara.data.mapper;

import com.oguzhanozgokce.lab6_0505_marmara.data.model.TransactionEntity;
import com.oguzhanozgokce.lab6_0505_marmara.domain.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionMapper {

    public static Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;
        return new Transaction(
                entity.id,
                entity.personName != null ? entity.personName : "",
                entity.transactionType != null ? entity.transactionType : "",
                entity.amount != null ? entity.amount : 0.0,
                entity.date != null ? entity.date : ""
        );
    }

    public static TransactionEntity toEntity(Transaction domain) {
        if (domain == null) return null;
        TransactionEntity entity = new TransactionEntity();
        if (domain.getId() > 0) {
            entity.id = domain.getId();
        }
        entity.personName = domain.getPersonName();
        entity.transactionType = domain.getTransactionType();
        entity.amount = domain.getAmount();
        entity.date = domain.getDate();
        return entity;
    }

    public static List<Transaction> toDomainList(List<TransactionEntity> entityList) {
        List<Transaction> domainList = new ArrayList<>();
        if (entityList != null) {
            for (TransactionEntity entity : entityList) {
                Transaction domain = toDomain(entity);
                if (domain != null) {
                    domainList.add(domain);
                }
            }
        }
        return domainList;
    }

    public static List<TransactionEntity> toEntityList(List<Transaction> domainList) {
        List<TransactionEntity> entityList = new ArrayList<>();
        if (domainList != null) {
            for (Transaction domain : domainList) {
                TransactionEntity entity = toEntity(domain);
                if (entity != null) {
                    entityList.add(entity);
                }
            }
        }
        return entityList;
    }
}
