package com.traveleasy.pathbase.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import com.traveleasy.pathbase.R;


public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView shareTextView = findViewById(R.id.shareTxt);
        CardView share = findViewById(R.id.share);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Make Your Journey Easy With This Travelling App";
                String sub = "https://play.google.com/store/apps/details?id=com.traveleasy.pathbase";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, sub);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });


        String fullText = "Share\nTap to share";

        SpannableString spannableString = new SpannableString(fullText);

        int textSizeInPx = (int) (20 * getResources().getDisplayMetrics().density);

        spannableString.setSpan(
                new AbsoluteSizeSpan(textSizeInPx),
                fullText.indexOf("Tap to share"),
                fullText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        spannableString.setSpan(
                new ForegroundColorSpan(Color.GRAY),
                fullText.indexOf("Tap to share"),
                fullText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        shareTextView.setText(spannableString);

    }
}
