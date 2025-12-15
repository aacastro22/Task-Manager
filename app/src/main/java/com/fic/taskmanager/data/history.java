package com.fic.taskmanager.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class history {
    @PrimaryKey(autoGenerate = true)
    public int history_id;

    public String action;
    public String created_at;
    public String details;

    public history(String action, String created_at, String details) {
        this.action = action;
        this.created_at = created_at;
        this.details = details;
    }
}
