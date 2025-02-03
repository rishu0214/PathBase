package com.traveleasy.pathbase.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.traveleasy.pathbase.Model.Notes;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    private static volatile RoomDb instance; // Use volatile for thread safety

    public abstract MainDAO mainDAO();

    public static RoomDb getInstance(Context context) {
        if (instance == null) {
            synchronized (RoomDb.class) { // Ensure thread safety
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    RoomDb.class,
                                    "room_database"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
