package com.oguzhanozgokce.lab6_0505_marmara.ui.add;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.oguzhanozgokce.lab6_0505_marmara.databinding.ActivityAddBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddActivity extends AppCompatActivity {


    private ActivityAddBinding binding;
    private AddActivityViewModel viewModel;
    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AddActivityViewModel.class);



        setupDatePicker();
        setupButtonClick();
        observeViewModel();
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        binding.edittextDate.setOnClickListener(v -> new DatePickerDialog(AddActivity.this, dateSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        binding.edittextDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void setupButtonClick() {
        binding.buttonAddTransaction.setOnClickListener(v -> {
            String name = binding.edittextNameSurname.getText().toString().trim();
            String type = binding.spinnerTransactionType.getSelectedItem().toString();
            String amount = binding.edittextAmount.getText().toString().trim();
            String date = binding.edittextDate.getText().toString().trim();

            viewModel.addTransaction(name, type, amount, date);
        });
    }

    private void observeViewModel() {
        viewModel.getLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.buttonAddTransaction.setEnabled(!isLoading);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();

            }
        });

        viewModel.getSuccess().observe(this, isSuccess -> {
            if (Boolean.TRUE.equals(isSuccess)) {
                Toast.makeText(this, "İşlem başarıyla eklendi!", Toast.LENGTH_SHORT).show();
                viewModel.clearSuccess();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}