package com.traveleasy.pathbase.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.traveleasy.pathbase.Model.Transaction;
import com.traveleasy.pathbase.Utils.Constants;
import java.util.Calendar;
import java.util.Date;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
    public MutableLiveData<RealmResults<Transaction>> categoriesTransactions = new MutableLiveData<>();

    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();

    private Realm realm;
    private Calendar calendar;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setupDatabase();
    }

    private void setupDatabase() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
                        if (oldVersion == 1) {
                            if (realm.getSchema().contains("Transaction")) {
                                realm.getSchema().get("Transaction")
                                        .addIndex("id")
                                        .addPrimaryKey("id");
                            }
                            oldVersion++;
                        }
                    }
                })
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void getTransactions(Calendar calendar, String type) {
        this.calendar = calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        RealmResults<Transaction> newTransactions = null;
        if(Constants.SELECTED_TAB_STATS == Constants.DAILY) {
            newTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", type)
                    .findAll();

        } else if(Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH,0);

            Date startTime = calendar.getTime();


            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();

            newTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .equalTo("type", type)
                    .findAll();
        }


        categoriesTransactions.setValue(newTransactions);

    }

    public void getTransactions(Calendar selectedDate) {
        if (realm == null || selectedDate == null) return;

        calendar = (Calendar) selectedDate.clone();
        resetCalendarTime(calendar);

        double income = 0, expense = 0, total = 0;
        RealmResults<Transaction> newTransactions;

        if (Constants.SELECTED_TAB == Constants.DAILY) {
            Date startOfDay = calendar.getTime();
            Date endOfDay = new Date(calendar.getTimeInMillis() + (24 * 60 * 60 * 1000));

            newTransactions = fetchTransactionsForPeriod(startOfDay, endOfDay);

            income = calculateSumForPeriod(Constants.INCOME, startOfDay, endOfDay);
            expense = calculateSumForPeriod(Constants.EXPENSE, startOfDay, endOfDay);

        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startOfMonth = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endOfMonth = calendar.getTime();

            newTransactions = fetchTransactionsForPeriod(startOfMonth, endOfMonth);

            income = calculateSumForPeriod(Constants.INCOME, startOfMonth, endOfMonth);
            expense = calculateSumForPeriod(Constants.EXPENSE, startOfMonth, endOfMonth);

        } else {
            newTransactions = realm.where(Transaction.class).findAll();
        }

        total = income - expense;

        totalIncome.postValue(income);
        totalExpense.postValue(expense);
        totalAmount.postValue(total);

        transactions.postValue(newTransactions);

        Log.d("MainViewModel", "Income: " + income + ", Expense: " + expense + ", Total: " + total);

    }



    public void getAllTransactions() {
        if (realm == null) return;
        RealmResults<Transaction> allTransactions = realm.where(Transaction.class).findAll();
        transactions.setValue(allTransactions);
    }

    public void addTransaction(Transaction transaction) {
        if (realm == null || transaction == null) return;

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(transaction);
            realm.commitTransaction();

            if (calendar != null) {
                getTransactions(calendar);
            } else {
                getAllTransactions();
            }
        } catch (Exception e) {
            if (realm.isInTransaction()) realm.cancelTransaction();
        }
    }

    public void deleteTransaction(Transaction transaction) {
        if (realm == null || transaction == null) return;

        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();

        if (calendar != null) {
            getTransactions(calendar);
        } else {
            getAllTransactions();
        }
    }

    private void resetCalendarTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private RealmResults<Transaction> fetchTransactionsForPeriod(Date startTime, Date endTime) {
        return realm.where(Transaction.class)
                .greaterThanOrEqualTo("date", startTime)
                .lessThan("date", endTime)
                .findAll();
    }


    private double calculateSumForPeriod(String type, Date startTime, Date endTime) {
        Number sum = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date", startTime)
                .lessThan("date", endTime)
                .equalTo("type", type)
                .sum("amount");
        return sum != null ? sum.doubleValue() : 0;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
