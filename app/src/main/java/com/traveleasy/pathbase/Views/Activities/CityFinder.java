package com.traveleasy.pathbase.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.traveleasy.pathbase.R;

public class CityFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        final EditText editText = findViewById(R.id.searchCity);
        Button findWeather = findViewById(R.id.FindWeather);
        ImageView backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = editText.getText().toString().trim();
                if (!newCity.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("City", newCity);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
