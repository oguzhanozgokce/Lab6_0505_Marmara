package com.oguzhanozgokce.lab6_0505_marmara.ui.update;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.oguzhanozgokce.lab6_0505_marmara.R;
import com.oguzhanozgokce.lab6_0505_marmara.databinding.ActivityUpdateBinding;

import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UpdateActivity extends AppCompatActivity {

    private ActivityUpdateBinding binding;
    private UpdateActivityViewModel viewModel;
    private int transactionId;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(UpdateActivityViewModel.class);
        setupDatePicker();
        loadIntentData();
        observeViewModel();

        binding.buttonUpdateTransaction.setOnClickListener(v -> {
            viewModel.updateTransaction(
                    transactionId,
                    binding.edittextNameSurname.getText().toString().trim(),
                    binding.spinnerTransactionType.getSelectedItem().toString(),
                    binding.edittextAmount.getText().toString().trim(),
                    binding.edittextDate.getText().toString().trim()
            );
        });
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        transactionId = intent.getIntExtra("transaction_id", -1);

        String name = intent.getStringExtra("person_name");
        String type = intent.getStringExtra("transaction_type");
        double amount = intent.getDoubleExtra("amount", 0);
        String date = intent.getStringExtra("date");

        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>)
                binding.spinnerTransactionType.getAdapter();
        int pos = adapter.getPosition(type);
        binding.spinnerTransactionType.setSelection(pos);

        binding.edittextNameSurname.setText(name);
        binding.edittextAmount.setText(String.format(Locale.getDefault(), "%.2f", amount));
        binding.edittextDate.setText(date);
    }

    private void setupDatePicker() {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Tarih seç")
                .build();
        binding.edittextDate.setFocusable(false);
        binding.edittextDate.setClickable(true);
        binding.edittextDate.setOnClickListener(v -> picker.show(getSupportFragmentManager(), "DP"));
        picker.addOnPositiveButtonClickListener(selection ->
                binding.edittextDate.setText(picker.getHeaderText())
        );
    }

    private void observeViewModel() {
        viewModel.getLoading().observe(this, isLoading -> {
            binding.buttonUpdateTransaction.setEnabled(!isLoading);
        });
        viewModel.getError().observe(this, err -> {
            if (err != null && !err.isEmpty()) {
                Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
        viewModel.getSuccess().observe(this, ok -> {
            if (Boolean.TRUE.equals(ok)) {
                Toast.makeText(this, "Güncelleme başarılı", Toast.LENGTH_SHORT).show();
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