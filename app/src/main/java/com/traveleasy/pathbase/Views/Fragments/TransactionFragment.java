package com.traveleasy.pathbase.Views.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.traveleasy.pathbase.Adapter.TransactionsAdapter;
import com.traveleasy.pathbase.Model.Transaction;
import com.traveleasy.pathbase.Utils.Constants;
import com.traveleasy.pathbase.Utils.Helper;
import com.traveleasy.pathbase.ViewModels.MainViewModel;
import com.traveleasy.pathbase.databinding.FragmentTransactionBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionFragment extends Fragment {

    public TransactionFragment() {
        // Required empty public constructor
    }

    private FragmentTransactionBinding binding;
    private Calendar calendar;
    private MainViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Listen for transaction updates
        getParentFragmentManager().setFragmentResultListener("transaction_updated", this, (requestKey, bundle) -> {
            viewModel.getTransactions(calendar); // Refresh transactions when notified
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        updateDate();

        TransactionsAdapter adapter = new TransactionsAdapter(getContext(), new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.transactions.observe(getViewLifecycleOwner(), transactions -> {
            if (transactions != null) {
                List<Transaction> filteredTransactions = filterTransactions(transactions);
                adapter.updateTransactions(filteredTransactions);

                if (!filteredTransactions.isEmpty()) {
                    binding.emptyState.setVisibility(View.GONE);
                    binding.emptyText.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                } else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                    binding.emptyText.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                }
            }
        });

        viewModel.totalIncome.observe(getViewLifecycleOwner(), income -> {
            if (income != null) {
                binding.incomeLbl.setText(String.format("₹ %.2f", income));
            }
        });

        viewModel.totalExpense.observe(getViewLifecycleOwner(), expense -> {
            if (expense != null) {
                binding.expenseLbl.setText(String.format("₹ %.2f", expense));
            }
        });

        viewModel.totalAmount.observe(getViewLifecycleOwner(), total -> {
            if (total != null) {
                binding.totalLbl.setText(String.format("₹ %.2f", total));
            }
        });

        binding.previousDateBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1);
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.nextDateBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1);
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });

        // Open AddTransactionFragment on add button click
        binding.addBtn.setOnClickListener(view -> {
            new AddTransactionFragment().show(getParentFragmentManager(), null);
        });

        // TabLayout to toggle between Daily and Monthly tabs
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Monthly")) {
                    Constants.SELECTED_TAB = Constants.MONTHLY;
                } else if (tab.getText().equals("Daily")) {
                    Constants.SELECTED_TAB = Constants.DAILY;
                }
                updateDate();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return binding.getRoot();
    }

    // Update date and refresh transactions
    private void updateDate() {
        if (Constants.SELECTED_TAB == Constants.DAILY) {
            binding.currentDate.setText(Helper.formatDate(calendar.getTime())); // Daily format
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime())); // Monthly format
        }
        viewModel.getTransactions(calendar);
    }

    // Filter transactions based on the selected tab
    private List<Transaction> filterTransactions(List<Transaction> transactions) {
        if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            return Helper.filterMonthlyTransactions(transactions, calendar);
        }
        return transactions; // Daily transactions are directly returned
    }
}

