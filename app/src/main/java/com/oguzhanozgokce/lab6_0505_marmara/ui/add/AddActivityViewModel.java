package com.oguzhanozgokce.lab6_0505_marmara.ui.add;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhanozgokce.lab6_0505_marmara.domain.Transaction;
import com.oguzhanozgokce.lab6_0505_marmara.domain.TransactionRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddActivityViewModel extends ViewModel {

    private final TransactionRepository repository;
    private final Executor executor;

    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _success = new MutableLiveData<>();

    @Inject
    public AddActivityViewModel(TransactionRepository repository) {
        this.repository = repository;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<Boolean> getLoading() {
        return _loading;
    }

    public LiveData<String> getError() {
        return _error;
    }

    public LiveData<Boolean> getSuccess() {
        return _success;
    }

    public void clearError() {
        _error.setValue(null);
    }

    public void clearSuccess() {
        _success.setValue(false);
    }



    public void addTransaction(String name, String type, String amountStr, String date) {

        if (name == null || name.trim().isEmpty()) {
            _error.setValue("İsim boş olamaz");
            return;
        }
        if (type == null || type.trim().isEmpty()) {
            _error.setValue("İşlem türü seçiniz");
            return;
        }
        if (amountStr == null || amountStr.trim().isEmpty()) {
            _error.setValue("Tutar giriniz");
            return;
        }

        final double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            _error.setValue("Tutarı doğru giriniz");
            return;
        }

        if (date == null || date.trim().isEmpty()) {
            _error.setValue("Tarih seçiniz");
            return;
        }

        _loading.setValue(true);
        executor.execute(() -> {
            Transaction t = new Transaction(name, type, amount, date);
            repository.addTransaction(t);

            new Handler(Looper.getMainLooper()).post(() -> {
                _loading.setValue(false);
                _success.setValue(true);
            });
        });
    }
}
