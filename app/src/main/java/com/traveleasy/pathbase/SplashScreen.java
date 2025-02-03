package com.traveleasy.pathbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.traveleasy.pathbase.Views.Activities.MainActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent SpsIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(SpsIntent);
                finish();
            }
        }, 2500);
    }

}