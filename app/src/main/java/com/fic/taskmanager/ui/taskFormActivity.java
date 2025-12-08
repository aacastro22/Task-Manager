package com.fic.taskmanager.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fic.taskmanager.R;
import com.fic.taskmanager.data.appDatabase;
import com.fic.taskmanager.data.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class taskFormActivity extends AppCompatActivity {

    private appDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        db = appDatabase.getDatabase(this);

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etDescription = findViewById(R.id.etDescription);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDescription.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            task newtask = new task(title, desc, createdAt, false);

            Executors.newSingleThreadExecutor().execute(() -> {
                db.taskDao().insertTask(newtask);
                finish();
            });
        });
    }
}
