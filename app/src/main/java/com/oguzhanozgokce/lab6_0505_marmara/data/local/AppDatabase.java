package com.oguzhanozgokce.lab6_0505_marmara.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.oguzhanozgokce.lab6_0505_marmara.data.model.TransactionEntity;

@Database(
    entities = { TransactionEntity.class },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}