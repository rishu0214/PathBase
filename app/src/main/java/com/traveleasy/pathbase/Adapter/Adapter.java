package com.traveleasy.pathbase.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traveleasy.pathbase.Views.CheckList;
import com.traveleasy.pathbase.Constants.MyConstants;
import com.traveleasy.pathbase.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    List<String> title;
    List<Integer> images;
    LayoutInflater inflater;
    Activity activity;

    public Adapter(Context context, List<String> title, List<Integer> images, Activity activity) {
        this.title = title;
        this.images = images;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bag_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.title.setText(title.get(position));
        holder.img.setImageResource(images.get(position));
        holder.linearLayout.setAlpha(0.8F);

        holder.linearLayout.setOnClickListener(view -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(view.getContext(), CheckList.class);
                intent.putExtra(MyConstants.HEADER_SMALL, title.get(currentPosition));
                if (MyConstants.MY_SELECTIONS.equals(title.get(currentPosition))) {
                    intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.FALSE_STRING);
                } else {
                    intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.TRUE_STRING);
                }
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;
        LinearLayout linearLayout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            linearLayout = itemView.findViewById(R.id.ll1);
        }
    }
}
