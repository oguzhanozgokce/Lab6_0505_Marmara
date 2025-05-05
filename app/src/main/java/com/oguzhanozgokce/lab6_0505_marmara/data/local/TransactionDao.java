package com.oguzhanozgokce.lab6_0505_marmara.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.oguzhanozgokce.lab6_0505_marmara.data.model.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    long insert(TransactionEntity entity);

    @Delete
    int delete(TransactionEntity entity);

    @Update
    int update(TransactionEntity entity);

    @Query("SELECT * FROM transactions")
    List<TransactionEntity> getAll();
}