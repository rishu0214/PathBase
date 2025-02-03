package com.traveleasy.pathbase.Inteface;

import androidx.cardview.widget.CardView;

import com.traveleasy.pathbase.Model.Notes;

public interface NotesClickListener {
    void onClik(Notes notes);
    void onLongPress(Notes notes, CardView cardView);
}
