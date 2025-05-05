package com.oguzhanozgokce.lab6_0505_marmara.ui.mainList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oguzhanozgokce.lab6_0505_marmara.R;
import com.oguzhanozgokce.lab6_0505_marmara.databinding.ActivityMainBinding;
import com.oguzhanozgokce.lab6_0505_marmara.domain.Transaction;
import com.oguzhanozgokce.lab6_0505_marmara.ui.add.AddActivity;
import com.oguzhanozgokce.lab6_0505_marmara.ui.update.UpdateActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private TransactionAdapter adapter;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        setupRecyclerView();
        setupFab();
        observeTransactions();
    }

    private void setupRecyclerView() {
        adapter = new TransactionAdapter();
        adapter.setOnItemLongClickListener(this::showOptionsDialog);
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTransactions.setAdapter(adapter);
    }

    private void setupFab() {
        binding.fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddActivity.class))
        );
    }

    private void observeTransactions() {
        viewModel.getTransactions().observe(this, list -> {
            adapter.submitList(list);
        });
    }

    private void showOptionsDialog(Transaction t) {
        String[] options = { "Güncelle", "Sil" };
        new AlertDialog.Builder(this)
                .setTitle("Seçiminiz")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(this, UpdateActivity.class);
                        intent.putExtra("transaction_id", t.getId());
                        intent.putExtra("person_name", t.getPersonName());
                        intent.putExtra("transaction_type", t.getTransactionType());
                        intent.putExtra("amount", t.getAmount());
                        intent.putExtra("date", t.getDate());
                        startActivity(intent);
                    } else {
                        viewModel.deleteTransaction(t);
                        viewModel.loadTransactions();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadTransactions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}