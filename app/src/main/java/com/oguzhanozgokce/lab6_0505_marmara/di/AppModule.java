package com.oguzhanozgokce.lab6_0505_marmara.di;

import android.content.Context;
import androidx.room.Room;
import com.oguzhanozgokce.lab6_0505_marmara.data.local.AppDatabase;
import com.oguzhanozgokce.lab6_0505_marmara.data.local.TransactionDao;
import com.oguzhanozgokce.lab6_0505_marmara.data.repository.TransactionRepositoryImpl;
import com.oguzhanozgokce.lab6_0505_marmara.domain.TransactionRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public AppDatabase provideDatabase(@ApplicationContext Context ctx) {
        return Room.databaseBuilder(ctx, AppDatabase.class, "marmara-db")
                   .fallbackToDestructiveMigration()
                   .build();
    }

    @Provides
    @Singleton
    public TransactionDao provideTransactionDao(AppDatabase db) {
        return db.transactionDao();
    }

    @Provides
    @Singleton
    public TransactionRepository provideTransactionRepository(TransactionDao dao) {
        return new TransactionRepositoryImpl(dao);
    }
}
