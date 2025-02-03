package com.traveleasy.pathbase.Views.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Utils.Constants;
import com.traveleasy.pathbase.ViewModels.MainViewModel;
import com.traveleasy.pathbase.Views.Fragments.StatsFragment;
import com.traveleasy.pathbase.Views.Fragments.TransactionFragment;
import com.traveleasy.pathbase.databinding.ActivityWalletHomePageBinding;

import java.util.Calendar;

public class WalletHomePage extends AppCompatActivity {

    ActivityWalletHomePageBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;
    private boolean isTipsSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWalletHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Expense Tracker");

        Constants.setCategories();

        calendar = Calendar.getInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new TransactionFragment())
                .commit();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            if (item.getItemId() == R.id.transactions) {
                selectedFragment = new TransactionFragment();
            } else if (item.getItemId() == R.id.stats) {
                selectedFragment = new StatsFragment();
            } else {
                return false;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, selectedFragment)
                    .commit();

            return true;
        });

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
            if (currentFragment instanceof TransactionFragment) {
                binding.bottomNavigationView.setSelectedItemId(R.id.transactions);
            } else if (currentFragment instanceof StatsFragment) {
                binding.bottomNavigationView.setSelectedItemId(R.id.stats);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_wallet, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tips) {
            if (!isTipsSelected) {
                isTipsSelected = true;
                setMenuItemIconTint(item, Color.YELLOW);
                showInfoDialog(item);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setMenuItemIconTint(MenuItem item, int color) {
        Drawable icon = item.getIcon();
        if (icon != null) {
            icon.mutate();
            icon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    private void showInfoDialog(MenuItem menuItem) {
        TextView messageTextView = new TextView(this);
        messageTextView.setText("Here are some tips to improve your experience:\n\n" +
                "1. Do not change the activity from transaction to stats while you are in the monthly tab of transaction activity.\n" +
                "2. Do not delete a transaction in the monthly tab of transaction activity, as it will require a refresh to show the available transactions.\n" +
                "3. Do not change the activity from stats to transaction while you are in the expense section of the daily tab in stats activity.\n" +
                "4. Do not change the activity from stats to transaction while you are in the monthly tab of stats activity.\n\n" +
                "If you encounter any of the above errors, then simply try to change tabs or restart the app.");
        messageTextView.setPadding(40, 40, 40, 40);
        messageTextView.setTextSize(16);
        messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            messageTextView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        TextView titleTextView = new TextView(this);
        titleTextView.setText("Tips for your convenience");
        titleTextView.setTextSize(20);
        titleTextView.setTextColor(getResources().getColor(R.color.title_text_color));
        titleTextView.setPadding(40, 40, 40, 40);
        titleTextView.setTypeface(null, Typeface.BOLD);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(titleTextView);
        builder.setView(messageTextView);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnDismissListener(d -> {
            isTipsSelected = false;
            setMenuItemIconTint(menuItem, Color.WHITE);
        });

        dialog.show();
    }


}
