package com.traveleasy.pathbase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.traveleasy.pathbase.Inteface.NotesClickListener;
import com.traveleasy.pathbase.Model.Notes;
import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Utils.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NoteListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Notes> notesList;
    NotesClickListener listener;

    public NoteListAdapter(Context context, List<Notes> notesList, NotesClickListener listener) {
        this.context = context;
        this.notesList = notesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.titleTxt.setText(notesList.get(position).getTitle());
        holder.notesTxt.setText(notesList.get(position).getNotes());

        // Convert the String date to a Date object and then format it
        String formattedDate = parseAndFormatDate(notesList.get(position).getDate());
        holder.dateTxt.setText(formattedDate);
        holder.dateTxt.setSelected(true);

        if (notesList.get(position).getPinned()) {
            holder.imageView.setImageResource(R.drawable.pin);
        } else {
            holder.imageView.setImageResource(0);
        }

        int color_code = getRandomColor();
        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code));

        holder.cardView.setOnClickListener(view -> listener.onClik(notesList.get(holder.getAdapterPosition())));

        holder.cardView.setOnLongClickListener(view -> {
            listener.onLongPress(notesList.get(holder.getAdapterPosition()), holder.cardView);
            return true;
        });
    }

    private String parseAndFormatDate(String timestamp) {
        try {
            // Parse the String into a Date object
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = inputFormat.parse(timestamp);

            // Format the Date into "d MMM, yyyy hh:mm a" (e.g., "8 Jan, 2025 05:30 PM")
            SimpleDateFormat outputFormat = new SimpleDateFormat("d MMM, yyyy hh:mm a", Locale.ENGLISH);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Return the original timestamp if parsing fails
            return timestamp;
        }
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color2);
        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void filterList(List<Notes> filterList) {
        notesList = filterList;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    TextView notesTxt, titleTxt, dateTxt;
    ImageView imageView;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.note_conatiner);
        notesTxt = itemView.findViewById(R.id.notesTxt);
        titleTxt = itemView.findViewById(R.id.titleTxt);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        imageView = itemView.findViewById(R.id.pinned);
    }
}
