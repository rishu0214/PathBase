package com.traveleasy.pathbase.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.traveleasy.pathbase.R;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Button closeButton = findViewById(R.id.bottom_sheet_close_button);

        closeButton.setOnClickListener(view -> {
            finish();
        });
    }
}