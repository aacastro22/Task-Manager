package com.fic.taskmanager.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface historyDao {

    @Insert
    void insertHistory(history history);

    @Query("SELECT * FROM history ORDER BY history_id DESC")
    List<history> getAllhistory();

    @Query("SELECT * FROM history WHERE `action` = :type ORDER BY history_id DESC")
    List<history> gethistoryByAction(String type);

    @Query("SELECT * FROM history WHERE created_at LIKE :date || '%' ORDER BY history_id DESC")
    List<history> gethistoryByDate(String date);
}
