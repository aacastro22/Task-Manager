package com.fic.taskmanager.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String task_title;
    private String task_description;
    private String created_at;
    private boolean is_completed;

    public task(String task_title, String task_description, String created_at, boolean is_completed) {
        this.task_title = task_title;
        this.task_description = task_description;
        this.created_at = created_at;
        this.is_completed = is_completed;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTask_title() { return task_title; }
    public void setTask_title(String task_title) { this.task_title = task_title; }

    public String getTask_description() { return task_description; }
    public void setTask_description(String task_description) { this.task_description = task_description; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public boolean isIs_completed() { return is_completed; }
    public void setIs_completed(boolean is_completed) { this.is_completed = is_completed; }
}
