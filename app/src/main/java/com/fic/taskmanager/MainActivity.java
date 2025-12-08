package com.fic.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.taskmanager.data.appDatabase;
import com.fic.taskmanager.data.task;
import com.fic.taskmanager.ui.taskFormActivity;
import com.fic.taskmanager.ui.adapter.taskAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private appDatabase db;
    private taskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = appDatabase.getDatabase(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new taskAdapter(new java.util.ArrayList<>());
        recyclerView.setAdapter(adapter);

        Button btnAddtask = findViewById(R.id.btnAddTask);
        btnAddtask.setOnClickListener(v ->
                startActivity(new Intent(this, taskFormActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<task> tasks = db.taskDao().getAllTasks();
            runOnUiThread(() -> adapter.updateData(tasks));
        });
    }
}
