package com.traveleasy.pathbase.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traveleasy.pathbase.R;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        ImageView q1dd = findViewById(R.id.q1dd);
        ImageView q1du = findViewById(R.id.q1du);
        ImageView q2dd = findViewById(R.id.q2dd);
        ImageView q2du = findViewById(R.id.q2du);
        ImageView q3dd = findViewById(R.id.q3dd);
        ImageView q3du = findViewById(R.id.q3du);
        ImageView q4dd = findViewById(R.id.q4dd);
        ImageView q4du = findViewById(R.id.q4du);

        LinearLayout question1_container = findViewById(R.id.question1_container);
        final TextView answer1 = findViewById(R.id.answer1);

        question1_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1.getVisibility() == View.GONE) {

                    Animation fadeIn = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_in);
                    answer1.startAnimation(fadeIn);
                    answer1.setVisibility(View.VISIBLE);
                    q1dd.setVisibility(View.GONE);
                    q1du.setVisibility(View.VISIBLE);

                } else {
                    Animation fadeOut = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_out);
                    answer1.startAnimation(fadeOut);
                    answer1.setVisibility(View.GONE);
                    q1dd.setVisibility(View.VISIBLE);
                    q1du.setVisibility(View.GONE);
                }
            }
        }); //

        LinearLayout question2_container = findViewById(R.id.question2_container);
        final TextView answer2 = findViewById(R.id.answer2);
        question2_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer2.getVisibility() == View.GONE) {

                    Animation fadeIn = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_in);
                    answer2.startAnimation(fadeIn);
                    answer2.setVisibility(View.VISIBLE);
                    q2dd.setVisibility(View.GONE);
                    q2du.setVisibility(View.VISIBLE);

                } else {
                    Animation fadeOut = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_out);
                    answer2.startAnimation(fadeOut);
                    answer2.setVisibility(View.GONE);
                    q2dd.setVisibility(View.VISIBLE);
                    q2du.setVisibility(View.GONE);
                }
            }
        }); //

        LinearLayout question3_container = findViewById(R.id.question3_container);
        final TextView answer3 = findViewById(R.id.answer3);

        question3_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer3.getVisibility() == View.GONE) {

                    Animation fadeIn = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_in);
                    answer3.startAnimation(fadeIn);
                    answer3.setVisibility(View.VISIBLE);
                    q3dd.setVisibility(View.GONE);
                    q3du.setVisibility(View.VISIBLE);

                } else {
                    Animation fadeOut = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_out);
                    answer3.startAnimation(fadeOut);
                    answer3.setVisibility(View.GONE);
                    q3dd.setVisibility(View.VISIBLE);
                    q3du.setVisibility(View.GONE);
                }
            }
        }); //

        LinearLayout question4_container = findViewById(R.id.question4_container);
        final TextView answer4 = findViewById(R.id.answer4);

        question4_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer4.getVisibility() == View.GONE) {

                    Animation fadeIn = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_in);
                    answer4.startAnimation(fadeIn);
                    answer4.setVisibility(View.VISIBLE);
                    q4dd.setVisibility(View.GONE);
                    q4du.setVisibility(View.VISIBLE);

                } else {
                    Animation fadeOut = AnimationUtils.loadAnimation(SupportActivity.this, R.anim.fade_out);
                    answer4.startAnimation(fadeOut);
                    answer4.setVisibility(View.GONE);
                    q4dd.setVisibility(View.VISIBLE);
                    q4du.setVisibility(View.GONE);
                }
            }
        }); //
    }
}