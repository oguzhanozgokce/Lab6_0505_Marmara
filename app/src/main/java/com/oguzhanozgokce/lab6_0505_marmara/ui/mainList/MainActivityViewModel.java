package com.oguzhanozgokce.lab6_0505_marmara.ui.mainList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhanozgokce.lab6_0505_marmara.domain.Transaction;
import com.oguzhanozgokce.lab6_0505_marmara.domain.TransactionRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    private final TransactionRepository repository;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Transaction>> _transactions = new MutableLiveData<>();
    public LiveData<List<Transaction>> getTransactions() {
        return _transactions;
    }

    @Inject
    public MainActivityViewModel(TransactionRepository repository) {
        this.repository = repository;
        loadTransactions();
    }

    public void loadTransactions() {
        executor.execute(() -> {
            List<Transaction> list = repository.getAllTransactions();
            _transactions.postValue(list);
        });
    }

    public void deleteTransaction(Transaction t) {
        executor.execute(() -> {
            repository.deleteTransaction(t);
        });
    }
}
