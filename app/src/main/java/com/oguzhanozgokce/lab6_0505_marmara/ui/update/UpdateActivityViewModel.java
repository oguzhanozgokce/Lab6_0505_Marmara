package com.oguzhanozgokce.lab6_0505_marmara.ui.update;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhanozgokce.lab6_0505_marmara.domain.Transaction;
import com.oguzhanozgokce.lab6_0505_marmara.domain.TransactionRepository;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UpdateActivityViewModel extends ViewModel {

    private final TransactionRepository repository;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>(false);
    private final MutableLiveData<String>  _error   = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _success = new MutableLiveData<>();

    @Inject
    public UpdateActivityViewModel(TransactionRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> getLoading() { return _loading; }
    public LiveData<String>  getError()   { return _error;   }
    public LiveData<Boolean> getSuccess() { return _success; }

    public void clearError()   { _error.setValue(null);    }
    public void clearSuccess() { _success.setValue(false); }

    /**
     * Güncelleme isteği.
     * @param id        Güncellenecek transaction ID
     */
    public void updateTransaction(int id,
                                  String name,
                                  String type,
                                  String amountStr,
                                  String date) {

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
        try {
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .parse(date);
        } catch (Exception e) {
            _error.setValue("Tarih formatı dd/MM/yyyy olmalı");
            return;
        }

        // Repository çağrısı
        _loading.setValue(true);
        executor.execute(() -> {
            Transaction t = new Transaction(id, name, type, amount, date);
            repository.updateTransaction(t);
            _loading.postValue(false);
            _success.postValue(true);
        });
    }
}