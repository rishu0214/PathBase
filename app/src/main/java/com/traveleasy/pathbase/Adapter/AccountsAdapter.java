package com.traveleasy.pathbase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.traveleasy.pathbase.Model.Account;
import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.databinding.RowAccountBinding;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder> {

    Context context;
    ArrayList<Account> accountArrayList;

    public interface AccountsClickListener {
        void onAccountSelected(Account account);
    }

    AccountsClickListener accountsClickListener;


    public AccountsAdapter(Context context, ArrayList<Account> accountArrayList, AccountsClickListener accountsClickListener) {
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.accountsClickListener = accountsClickListener;
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.accountName.setText(account.getAccountName());
        holder.binding.accountName.setTextColor(ContextCompat.getColor(context, R.color.title_text_color));
        holder.itemView.setOnClickListener(c-> {
            accountsClickListener.onAccountSelected(account);

        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder {

        RowAccountBinding binding;

        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountBinding.bind(itemView);
        }
    }
}