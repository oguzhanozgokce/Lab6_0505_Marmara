package com.oguzhanozgokce.lab6_0505_marmara.ui.mainList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.oguzhanozgokce.lab6_0505_marmara.R;
import com.oguzhanozgokce.lab6_0505_marmara.databinding.ItemTransactionBinding;
import com.oguzhanozgokce.lab6_0505_marmara.domain.Transaction;

import java.util.Locale;

public class TransactionAdapter
        extends ListAdapter<Transaction, TransactionAdapter.VH> {

    public TransactionAdapter() {
        super(DIFF_CALLBACK);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Transaction transaction);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    private static final DiffUtil.ItemCallback<Transaction> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Transaction>() {
                @Override
                public boolean areItemsTheSame(@NonNull Transaction oldItem,
                                               @NonNull Transaction newItem) {
                    return oldItem.getId() == newItem.getId();
                }
                @Override
                public boolean areContentsTheSame(@NonNull Transaction oldItem,
                                                  @NonNull Transaction newItem) {
                    return oldItem.getPersonName().equals(newItem.getPersonName())
                            && oldItem.getTransactionType().equals(newItem.getTransactionType())
                            && Double.compare(oldItem.getAmount(), newItem.getAmount()) == 0
                            && oldItem.getDate().equals(newItem.getDate());
                }
            };

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionBinding binding = ItemTransactionBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(binding, longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(getItem(position));
    }

    static class VH extends RecyclerView.ViewHolder {
        private final ItemTransactionBinding b;

        public VH(@NonNull ItemTransactionBinding binding,
                  OnItemLongClickListener longClickListener) {
            super(binding.getRoot());
            this.b = binding;

            b.getRoot().setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    Transaction t = (Transaction) v.getTag();
                    longClickListener.onItemLongClick(t);
                }
                return true;
            });
        }

        void bind(Transaction t) {
            b.getRoot().setTag(t);
            b.tvPersonName.setText(t.getPersonName());
            b.tvTransactionType.setText(t.getTransactionType());
            b.tvDate.setText(t.getDate());
            b.tvAmount.setText(
                    String.format(Locale.getDefault(), "%.2f", t.getAmount())
            );

            int colorRes = t.getTransactionType().equalsIgnoreCase("Gelir")
                    ? R.color.incomeBorder
                    : R.color.expenseBorder;
            int color = ContextCompat.getColor(b.getRoot().getContext(), colorRes);
            b.cardView.setStrokeColor(color);
        }
    }
}