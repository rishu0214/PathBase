package com.traveleasy.pathbase.Views.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.traveleasy.pathbase.Adapter.AccountsAdapter;
import com.traveleasy.pathbase.Adapter.CategoryAdapter;
import com.traveleasy.pathbase.Model.Account;
import com.traveleasy.pathbase.Model.Transaction;
import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Utils.Constants;
import com.traveleasy.pathbase.Utils.Helper;
import com.traveleasy.pathbase.Views.Activities.WalletHomePage;
import com.traveleasy.pathbase.databinding.FragmentAddTransactionBinding;
import com.traveleasy.pathbase.databinding.ListDialogBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionFragment extends BottomSheetDialogFragment {

    public AddTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddTransactionBinding binding;
    Transaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater);

        transaction = new Transaction();

        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.greyy));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.colorau3));

            transaction.setType(Constants.INCOME);
        });

        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.greyy));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.colorau4));

            transaction.setType(Constants.EXPENSE);
        });

        binding.date.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (datePicker, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String dateToShow = Helper.formatDate(calendar.getTime());
                        binding.date.setText(dateToShow);

                        transaction.setDate(calendar.getTime());
                        transaction.setId(calendar.getTime().getTime());
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            datePickerDialog.show();
        });


        binding.category.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());


            CategoryAdapter.CategoryClickListener listener = category -> {
                binding.category.setText(category.getCategoryName());

                transaction.setCategory(category.getCategoryName());

                categoryDialog.dismiss();
            };

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, listener);
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);
            categoryDialog.show();
        });

        binding.account.setOnClickListener(c-> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(dialogBinding.getRoot());

            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0, "Cash"));
            accounts.add(new Account(0, "Bank"));
            accounts.add(new Account(0, "UPI"));
            accounts.add(new Account(0, "Card"));
            accounts.add(new Account(0, "Other"));


            AccountsAdapter adapter = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.account.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());

                    accountsDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(adapter);

            accountsDialog.show();

        });

        binding.saveTransactionBtn.setOnClickListener(c -> {
            String amountStr = binding.amount.getText().toString();

            if (amountStr.isEmpty() || transaction.getCategory() == null || transaction.getAccount() == null || transaction.getType() == null) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid amount entered", Toast.LENGTH_SHORT).show();
                return;
            }

            if (transaction.getType().equals(Constants.EXPENSE)) {
                transaction.setAmount(amount * -1);
            } else {
                transaction.setAmount(amount);
            }

            // Save the transaction using the ViewModel
            ((WalletHomePage) getActivity()).viewModel.addTransaction(transaction);

            // Notify TransactionFragment about the update
            Bundle result = new Bundle();
            getParentFragmentManager().setFragmentResult("transaction_updated", result);

            // Dismiss the dialog
            dismiss();
        });

        return binding.getRoot();
    }
}
