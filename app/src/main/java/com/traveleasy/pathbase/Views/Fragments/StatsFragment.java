package com.traveleasy.pathbase.Views.Fragments;

import static com.traveleasy.pathbase.Utils.Constants.SELECTED_STATS_TYPE;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.material.tabs.TabLayout;
import com.traveleasy.pathbase.Model.Transaction;
import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Utils.Constants;
import com.traveleasy.pathbase.Utils.Helper;
import com.traveleasy.pathbase.ViewModels.MainViewModel;
import com.traveleasy.pathbase.databinding.FragmentStatsBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;

public class StatsFragment extends Fragment {

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentStatsBinding binding;
    Calendar calendar;
    int savedDay;
    public MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStatsBinding.inflate(inflater);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        savedDay = calendar.get(Calendar.DAY_OF_MONTH);
        updateDate();

        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.grey));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.colorau3));

            SELECTED_STATS_TYPE = Constants.INCOME;
            updateDate();
        });

        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.grey));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.colorau4));

            SELECTED_STATS_TYPE = Constants.EXPENSE;
            updateDate();
        });

        binding.previousDateBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1);
                savedDay = calendar.get(Calendar.DAY_OF_MONTH);
            } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.nextDateBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1);
                savedDay = calendar.get(Calendar.DAY_OF_MONTH);
            } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Monthly")) {
                    Constants.SELECTED_TAB_STATS = Constants.MONTHLY;
                    // Set the calendar to the first day of the month
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    updateDate();
                } else if (tab.getText().equals("Daily")) {
                    Constants.SELECTED_TAB_STATS = Constants.DAILY;
                    calendar.set(Calendar.DAY_OF_MONTH, savedDay);
                    updateDate();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        Pie pie = AnyChart.pie();

        pie.title("Category-wise Distribution")
                .title().fontSize(16).fontColor("#2E2E2E").enabled(true);

        pie.legend()
                .position("top")
                .itemsLayout("horizontal")
                .align("center")
                .fontSize(14)
                .padding(0, 0, 10, 0);

        pie.labels()
                .position("outside")
                .format("{%X}")
                .fontColor("#2E2E2E")
                .fontSize(14)
                .fontWeight("bold")
                .enabled(true);


        pie.innerRadius("30%");
        pie.radius("90%");


        viewModel.categoriesTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                if (transactions.size() > 0) {
                    binding.emptyState.setVisibility(View.GONE);
                    binding.emptyText.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);

                    List<DataEntry> data = new ArrayList<>();

                    Map<String, Double> categoryMap = new HashMap<>();

                    for (Transaction transaction : transactions) {
                        String category = transaction.getCategory();
                        double amount = transaction.getAmount();

                        if (categoryMap.containsKey(category)) {
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal += Math.abs(amount);

                            categoryMap.put(category, currentTotal);
                        } else {
                            categoryMap.put(category, Math.abs(amount));
                        }
                    }

                    for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
                        data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
                    }
                    pie.data(data);

                } else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                    binding.emptyText.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getTransactions(calendar, SELECTED_STATS_TYPE);

        binding.anyChart.setChart(pie);

        return binding.getRoot();
    }

    private void updateDate() {
        if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransactions(calendar, SELECTED_STATS_TYPE);
    }
}
