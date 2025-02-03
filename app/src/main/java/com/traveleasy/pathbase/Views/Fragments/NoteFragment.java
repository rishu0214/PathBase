package com.traveleasy.pathbase.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.traveleasy.pathbase.Adapter.NoteListAdapter;
import com.traveleasy.pathbase.Database.RoomDb;
import com.traveleasy.pathbase.Inteface.NotesClickListener;
import com.traveleasy.pathbase.Model.Notes;
import com.traveleasy.pathbase.Views.Activities.NotesTakeActivity;
import com.traveleasy.pathbase.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    RoomDb database;
    List<Notes> notes = new ArrayList<>();
    Notes selectedNotes;
    FloatingActionButton fabBtn;
    SearchView searchView;
    private static final int TIME_INTERVAL = 2000;
    private long lastBackPressedTime = 0;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        recyclerView = view.findViewById(R.id.notesRv);
        fabBtn = view.findViewById(R.id.addBtn);
        searchView = view.findViewById(R.id.searchView);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);

        searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        database = RoomDb.getInstance(requireContext());

        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        if (searchEditText != null) {
            searchEditText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.greyy));

            searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        }


        executorService.execute(() -> {
            notes = database.mainDAO().getAllSorted();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> updateRecycle(notes));
            }
        });

        fabBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), NotesTakeActivity.class);
            startActivityForResult(intent, 101);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });

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

    private void filter(String newText) {
        newText = newText.trim();
        List<Notes> filterList = new ArrayList<>();
        for (Notes singleNote : notes) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(singleNote);
            }
        }
        noteListAdapter.filterList(filterList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            Notes newNotes = (Notes) data.getSerializableExtra("note");
            if (newNotes.getTitle().trim().isEmpty() && newNotes.getNotes().trim().isEmpty()) return;
            executorService.execute(() -> {
                newNotes.setTitle(newNotes.getTitle().trim());
                newNotes.setNotes(newNotes.getNotes().trim());
                database.mainDAO().insert(newNotes);
                notes.clear();
                notes.addAll(database.mainDAO().getAllSorted());
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> updateRecycle(notes));
                }
            });
        } else if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            Notes updatedNotes = (Notes) data.getSerializableExtra("note");
            if (updatedNotes.getTitle().trim().isEmpty() && updatedNotes.getNotes().trim().isEmpty()) return;
            executorService.execute(() -> {
                updatedNotes.setTitle(updatedNotes.getTitle().trim());
                updatedNotes.setNotes(updatedNotes.getNotes().trim());
                database.mainDAO().update(updatedNotes.getID(), updatedNotes.getTitle(), updatedNotes.getNotes());
                notes.clear();
                notes.addAll(database.mainDAO().getAllSorted());
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> updateRecycle(notes));
                }
            });
        }
    }

    private void updateRecycle(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        noteListAdapter = new NoteListAdapter(requireContext(), notes, notesClickListener);
        recyclerView.setAdapter(noteListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClik(Notes notes) {
            Intent intent = new Intent(requireContext(), NotesTakeActivity.class);
            intent.putExtra("old_notes", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongPress(Notes notes, CardView cardView) {
            selectedNotes = notes;
            showPop(cardView);
        }
    };

    private void showPop(CardView cardView) {
        Context wrapper = new ContextThemeWrapper(requireContext(), R.style.PopupMenuStyle);
        PopupMenu popupMenu = new PopupMenu(wrapper, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.pin) {
            executorService.execute(() -> {
                if (selectedNotes.getPinned()) {
                    database.mainDAO().pin(selectedNotes.getID(), false);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "UnPinned", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    database.mainDAO().pin(selectedNotes.getID(), true);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Pinned", Toast.LENGTH_SHORT).show());
                    }
                }
                notes.clear();
                notes.addAll(database.mainDAO().getAllSorted());
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> noteListAdapter.notifyDataSetChanged());
                }
            });
            return true;
        } else if (item.getItemId() == R.id.delete) {
            new android.app.AlertDialog.Builder(requireContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        executorService.execute(() -> {
                            database.mainDAO().delete(selectedNotes);
                            notes.clear();
                            notes.addAll(database.mainDAO().getAllSorted());
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> {
                                    noteListAdapter.notifyDataSetChanged();
                                    Toast.makeText(requireContext(), "Note is successfully deleted...", Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        return false;
    }
}