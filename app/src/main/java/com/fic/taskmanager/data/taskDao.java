package com.fic.taskmanager.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface taskDao {

    @Insert
    void insertTask(task task);

    @Update
    void updateTask(task task);

    @Delete
    void deleteTask(task task);

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    List<task> getAllTasks();
}
