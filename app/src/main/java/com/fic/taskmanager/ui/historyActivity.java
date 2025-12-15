package com.fic.taskmanager.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.taskmanager.R;
import com.fic.taskmanager.data.appDatabase;
import com.fic.taskmanager.data.history;
import com.fic.taskmanager.ui.adapter.historyAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class historyActivity extends AppCompatActivity {

    private appDatabase db;
    private historyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = appDatabase.getDatabase(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new historyAdapter(new java.util.ArrayList<>());
        recyclerView.setAdapter(adapter);

        EditText etFilter = findViewById(R.id.etFilter);
        Button btnFilter = findViewById(R.id.btnFilter);

        // Mostrar historial completo al inicio
        loadAllhistory();

        // Filtrar
        btnFilter.setOnClickListener(v -> {
            String filter = etFilter.getText().toString().trim();
            if (filter.isEmpty()) {
                loadAllhistory();
            } else if (filter.matches("\\d{4}-\\d{2}-\\d{2}")) {
                loadhistoryByDate(filter);
            } else {
                loadhistoryByAction(filter);
            }
        });
    }

    private void loadAllhistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<history> data = db.historyDao().getAllhistory();
            runOnUiThread(() -> adapter.updateData(data));
        });
    }

    private void loadhistoryByDate(String date) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<history> data = db.historyDao().gethistoryByDate(date);
            runOnUiThread(() -> adapter.updateData(data));
        });
    }

    private void loadhistoryByAction(String action) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<history> data = db.historyDao().gethistoryByAction(action);
            runOnUiThread(() -> adapter.updateData(data));
        });
    }
}
