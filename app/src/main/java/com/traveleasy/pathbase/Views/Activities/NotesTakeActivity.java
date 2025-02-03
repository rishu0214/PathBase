package com.traveleasy.pathbase.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.traveleasy.pathbase.Model.Notes;
import com.traveleasy.pathbase.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakeActivity extends AppCompatActivity {

    EditText titleED, notesEd;
    ImageView saveBtn;
    Notes notes;
    boolean isOldNotes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_take);

        saveBtn = findViewById(R.id.savebtn);
        titleED = findViewById(R.id.titleEdt);
        notesEd = findViewById(R.id.noteEdt);

        notes = (Notes) getIntent().getSerializableExtra("old_notes");
        if (notes != null) {
            titleED.setText(notes.getTitle());
            notesEd.setText(notes.getNotes());
            isOldNotes = true;
        }

        saveBtn.setOnClickListener(view -> {
            String title = titleED.getText().toString();
            String description = notesEd.getText().toString();

            if (description.isEmpty()) {
                Toast.makeText(NotesTakeActivity.this, "Please enter the description", Toast.LENGTH_SHORT).show();
                return;
            }
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
            Date date = new Date();

            if (!isOldNotes) {
                notes = new Notes();
            }

            notes.setTitle(title);
            notes.setNotes(description);
            notes.setDate(format.format(date));

            Intent intent = new Intent();
            intent.putExtra("note", notes);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
