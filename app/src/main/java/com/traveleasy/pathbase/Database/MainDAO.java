package com.traveleasy.pathbase.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.traveleasy.pathbase.Model.Notes;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll();

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE id = :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Notes note);

    @Query("UPDATE notes SET pinned = :pin WHERE id = :id")
    void pin(int id, boolean pin);

    @Query("SELECT * FROM notes ORDER BY pinned DESC, id DESC")
    List<Notes> getAllSorted();
}
