package com.traveleasy.pathbase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traveleasy.pathbase.Constants.MyConstants;
import com.traveleasy.pathbase.Database.RoomDBB;
import com.traveleasy.pathbase.Model.Items;
import com.traveleasy.pathbase.R;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder>{

    Context context;
    List<Items> itemsList;
    RoomDBB database;
    String show;

    public CheckListAdapter(){

    }

    public CheckListAdapter(Context context, List<Items> itemsList, RoomDBB database, String show) {
        this.context = context;
        this.itemsList = itemsList;
        this.database = database;
        this.show = show;
        if (itemsList.size() == 0){
            Toast.makeText(context.getApplicationContext(), "Nothing To Show", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListViewHolder(LayoutInflater.from(context).inflate(R.layout.check_list_item_bag, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        holder.checkBox.setText(itemsList.get(position).getItemname());
        holder.checkBox.setChecked(itemsList.get(position).getChecked());
//for check layout
        if (MyConstants.FALSE_STRING.equals(show)){
            holder.btnDelete.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_one));
        }else {
            if (itemsList.get(position).getChecked()){
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_two));
            }else {
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_one));
            }
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check = holder.checkBox.isChecked();
                database.mainDao().checkUncheck(itemsList.get(position).getID(), check);
                if (MyConstants.FALSE_STRING.equals(show)){
                    itemsList = database.mainDao().getAllSelected(true);
                    notifyDataSetChanged();
                }
                else {
                    itemsList.get(position).setChecked(check);
                    notifyDataSetChanged();
                    Toast toastMessage = null;
                    if (toastMessage != null){
                        toastMessage.cancel();
                    }
                    if (itemsList.get(position).getChecked()){
                        toastMessage = Toast.makeText(context, "(" + holder.checkBox.getText()+ ") Packed", Toast.LENGTH_SHORT);
                    }else {
                        toastMessage = Toast.makeText(context, "(" + holder.checkBox.getText()+ ") Un-Packed", Toast.LENGTH_SHORT);
                    }
                    toastMessage.show();
                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete ( "+itemsList.get(position).getItemname()+" )")
                        .setMessage("Are you Sure?")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.mainDao().delete(itemsList.get(position));
                                itemsList.remove(itemsList.get(position));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Canelled", Toast.LENGTH_SHORT).show();
                            }
                        }).setIcon(R.drawable.baseline_delete_forever_24).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}

class CheckListViewHolder extends RecyclerView.ViewHolder{

    LinearLayout layout;
    CheckBox checkBox;
    Button btnDelete;
    public CheckListViewHolder(@NonNull View itemView){
        super(itemView);
        layout = itemView.findViewById(R.id.linearLayoutt);
        checkBox = itemView.findViewById(R.id.checkbox);
        btnDelete = itemView.findViewById(R.id.btnDel);
    }
}