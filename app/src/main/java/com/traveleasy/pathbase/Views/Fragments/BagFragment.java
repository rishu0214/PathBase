package com.traveleasy.pathbase.Views.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveleasy.pathbase.Adapter.Adapter;
import com.traveleasy.pathbase.Constants.MyConstants;
import com.traveleasy.pathbase.Data.AppData;
import com.traveleasy.pathbase.Database.RoomDBB;
import com.traveleasy.pathbase.Model.Items;
import com.traveleasy.pathbase.R;

import java.util.ArrayList;
import java.util.List;

public class BagFragment extends Fragment {

    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    RoomDBB database;
    private static final int TIME_INTERVAL = 2000;
    private long lastBackPressedTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        addTitles();
        addImages();
        persistAppData();
        database = RoomDBB.getInstance(requireContext());
        List<Items> selectedItems = database.mainDao().getAllSelected(false);
        if (selectedItems != null && !selectedItems.isEmpty()) {
            System.out.println("----------------> " + selectedItems.get(0).getItemname());
        } else {
            System.out.println("----------------> No items found in the database.");
        }


        adapter = new Adapter(requireContext(), titles, images, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        handleBackPress();

        return view;
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

    private void persistAppData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = prefs.edit();

        database = RoomDBB.getInstance(requireContext());
        AppData appData = new AppData(database);
        int last = prefs.getInt(AppData.LAST_VERSION, 0);

        if (!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, false)){
            appData.persistAllData();
            editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, true);
            editor.commit();
        } else if (last<AppData.NEW_VERSION) {
            database.mainDao().deleteAllSystemItems(MyConstants.SYSTEM_SMALL);
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION);
            editor.commit();
        }
    }

    private void addTitles() {
        titles = new ArrayList<>();
        titles.add(MyConstants.BASIC_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.CLOTHING_CAMEL_CASE);
        titles.add(MyConstants.PERSONAL_CARE_CAMEL_CASE);
        titles.add(MyConstants.BABY_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.HEALTH_CAMEL_CASE);
        titles.add(MyConstants.TECHNOLOGY_CAMEL_CASE);
        titles.add(MyConstants.FOOD_CAMEL_CASE);
        titles.add(MyConstants.BEACH_SUPPLIES_CAMEL_CASE);
        titles.add(MyConstants.CAR_SUPPLIES_CAMEL_CASE);
        titles.add(MyConstants.NEEDS_CAMEL_CASE);
        titles.add(MyConstants.MY_LIST_CAMEL_CASE);
        titles.add(MyConstants.MY_SELECTIONS_CAMEL_CASE);
    }

    private void addImages() {
        images = new ArrayList<>();
        images.add(R.drawable.b1);
        images.add(R.drawable.b2);
        images.add(R.drawable.b3);
        images.add(R.drawable.b4);
        images.add(R.drawable.b5);
        images.add(R.drawable.b6);
        images.add(R.drawable.b7);
        images.add(R.drawable.b8);
        images.add(R.drawable.b9);
        images.add(R.drawable.b10);
        images.add(R.drawable.b11);
        images.add(R.drawable.b12);
    }
}
