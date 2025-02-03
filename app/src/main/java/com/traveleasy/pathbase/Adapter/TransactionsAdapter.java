package com.traveleasy.pathbase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traveleasy.pathbase.Model.Category;
import com.traveleasy.pathbase.Model.Transaction;
import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Utils.Constants;
import com.traveleasy.pathbase.Utils.Helper;
import com.traveleasy.pathbase.Views.Activities.WalletHomePage;
import com.traveleasy.pathbase.databinding.RowTransactionBinding;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    private final Context context;
    private List<Transaction> transactions;

    public TransactionsAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RowTransactionBinding binding = RowTransactionBinding.inflate(inflater, parent, false);
        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.transactionCategory.setText(transaction.getCategory());

        String formattedDate = Helper.formatDate(transaction.getDate());
        holder.binding.transactionDate.setText(formattedDate);

        holder.binding.accountLbl.setText(transaction.getAccount());

        Category category = Constants.getCategoryDetails(transaction.getCategory());
        if (category != null) {
            holder.binding.categoryIcon.setImageResource(category.getCategoryImage());
            holder.binding.categoryIcon.setBackgroundResource(category.getCategoryColor());
        }

        if (transaction.getType().equals(Constants.INCOME)) {
            holder.binding.transactionAmount.setTextColor(context.getResources().getColor(R.color.colorau3_1));
            holder.binding.transactionAmount.setText(String.format(" ₹%.2f", transaction.getAmount()));
        } else if (transaction.getType().equals(Constants.EXPENSE)) {
            holder.binding.transactionAmount.setTextColor(context.getResources().getColor(R.color.colorau4_1));
            holder.binding.transactionAmount.setText(String.format(" ₹%.2f", transaction.getAmount()));
        }

        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
            deleteDialog.setTitle("Delete Transaction");
            deleteDialog.setMessage("Are you sure to delete this transaction?");
            deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
                ((WalletHomePage) context).viewModel.deleteTransaction(transaction);
            });
            deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> {
                deleteDialog.dismiss();
            });
            deleteDialog.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return transactions == null ? 0 : transactions.size();
    }

    public void updateTransactions(List<Transaction> newTransactions) {
        this.transactions = newTransactions;
        notifyDataSetChanged();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        RowTransactionBinding binding;

        public TransactionViewHolder(@NonNull RowTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
