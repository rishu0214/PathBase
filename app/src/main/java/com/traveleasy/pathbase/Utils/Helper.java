package com.traveleasy.pathbase.Utils;

import com.traveleasy.pathbase.Model.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Helper {
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM, yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    public static String formatDateByMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    public static List<Transaction> filterMonthlyTransactions(List<Transaction> transactions, Calendar calendar) {
        List<Transaction> monthlyTransactions = new ArrayList<>();
        int selectedMonth = calendar.get(Calendar.MONTH);
        int selectedYear = calendar.get(Calendar.YEAR);

        for (Transaction transaction : transactions) {
            Calendar transactionDate = Calendar.getInstance();
            transactionDate.setTime(transaction.getDate());

            if (transactionDate.get(Calendar.MONTH) == selectedMonth && transactionDate.get(Calendar.YEAR) == selectedYear) {
                monthlyTransactions.add(transaction);
            }
        }
        return monthlyTransactions;
    }



}
