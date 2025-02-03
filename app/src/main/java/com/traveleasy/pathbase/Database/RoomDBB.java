package com.traveleasy.pathbase.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.traveleasy.pathbase.Dao.ItemsDao;
import com.traveleasy.pathbase.Model.Items;

@Database(entities = {Items.class}, version = 1, exportSchema = false)
public abstract class RoomDBB extends RoomDatabase {

    private static RoomDBB database;
    private static final String DATABASE_NAME = "MyDb";

    public synchronized static RoomDBB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDBB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract ItemsDao mainDao();
}
