package com.traveleasy.pathbase.Views.Fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Views.Activities.WalletHomePage;

public class MapFragment extends Fragment {

    private static final int TIME_INTERVAL = 2000;
    private long lastBackPressedTime = 0;

    private ImageView imageView;
    private Button btngs;
    private int currentImageIndex = 0;
    private final int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private final Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        imageView = view.findViewById(R.id.imageView);
        btngs = view.findViewById(R.id.btngs);

        imageView.setVisibility(View.INVISIBLE);
        btngs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WalletHomePage.class);
                if (getContext() != null) {
                    startActivity(intent);
                }
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(images[currentImageIndex]);
                applyCoolAnimation(imageView);
                startImageAnimation();
            }
        }, 0);

        handleBackPress();

        return view;
    }

    private void startImageAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentImageIndex = (currentImageIndex + 1) % images.length;
                imageView.setImageResource(images[currentImageIndex]);
                applyCoolAnimation(imageView);
                handler.postDelayed(this, 3000);
            }
        }, 3000);
    }

    private void applyCoolAnimation(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.6f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.6f, 1f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, fadeIn);
        animatorSet.setDuration(1500);
        animatorSet.start();
    }

    private void handleBackPress() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (System.currentTimeMillis() - lastBackPressedTime < TIME_INTERVAL) {
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                    lastBackPressedTime = System.currentTimeMillis();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
