package com.fic.taskmanager.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {task.class}, version = 1)
public abstract class appDatabase extends RoomDatabase {

    private static volatile appDatabase INSTANCE;

    public abstract taskDao taskDao();

    public static appDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (appDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            appDatabase.class,
                            "task_manager_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
