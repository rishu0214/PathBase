package com.traveleasy.pathbase.Utils;

import com.traveleasy.pathbase.Model.Category;
import com.traveleasy.pathbase.R;

import java.util.ArrayList;

public class Constants {
    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

    public static ArrayList<Category> categories;

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;

    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constants.INCOME;

    public static void setCategories() {
        categories = new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.salary, R.color.black));
        categories.add(new Category("Business", R.drawable.business, R.color.black));
        categories.add(new Category("Investment", R.drawable.investment, R.color.black));
        categories.add(new Category("Loan", R.drawable.loan, R.color.black));
        categories.add(new Category("Rent", R.drawable.rent, R.color.black));
        categories.add(new Category("Other", R.drawable.other, R.color.black));
    }

    public static Category getCategoryDetails(String categoryName) {
        for (Category cat :
                categories) {
            if (cat.getCategoryName().equals(categoryName)) {
                return cat;
            }
        }
        return null;
    }

    public static int getAccountsColor(String accountName) {
        switch (accountName) {
            case "Bank":
                return R.color.category2;
            case "Cash":
                return R.color.category3;
            case "Card":
                return R.color.category4;
            default:
                return R.color.category5;
        }
    }

}