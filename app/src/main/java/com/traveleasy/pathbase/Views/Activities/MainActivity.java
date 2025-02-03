package com.traveleasy.pathbase.Views.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Views.AboutUs;
import com.traveleasy.pathbase.Views.Fragments.BagFragment;
import com.traveleasy.pathbase.Views.Fragments.MapFragment;
import com.traveleasy.pathbase.Views.Fragments.NoteFragment;
import com.traveleasy.pathbase.Views.Fragments.WeatherFragment;
import com.traveleasy.pathbase.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            binding.getRoot().getWindowVisibleDisplayFrame(r);
            int screenHeight = binding.getRoot().getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) {
                binding.bottomNavView.setVisibility(View.GONE);
            } else {
                binding.bottomNavView.setVisibility(View.VISIBLE);
            }
        });

        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        replaceFragment(new NoteFragment());

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_note) {
                replaceFragment(new NoteFragment());
            } else if (item.getItemId() == R.id.bottom_bag) {
                replaceFragment(new BagFragment());
            } else if (item.getItemId() == R.id.bottom_weather) {
                replaceFragment(new WeatherFragment());
            } else if (item.getItemId() == R.id.bottom_map) {
                replaceFragment(new MapFragment());
            } else {
                return false;
            }
            return true;
        });

        binding.navView.setNavigationItemSelectedListener(this::handleDrawerNavigation);
    }

    private boolean handleDrawerNavigation(@NonNull MenuItem item) {
        Intent intent = null;
        if (item.getItemId() == R.id.nav_home) {
            Toast.makeText(this, "You are at the HomePage", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.nav_setting) {
            intent = new Intent(this, SupportActivity.class);
        } else if (item.getItemId() == R.id.nav_share) {
            intent = new Intent(this, ShareActivity.class);
        } else if (item.getItemId() == R.id.nav_info) {
            intent = new Intent(this, AboutUs.class);
        } else {
            return false;
        }

        if (intent != null) {
            startActivity(intent);
        }
        binding.drawerLayout.closeDrawers();
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.navView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.guidebook) {
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.video) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Video Guide")
                    .setMessage("Want to see the video tutorial?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String youtubeUrl = "https://youtu.be/VAx2otnG7nw";
                            Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(youtubeUrl));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
