package com.fic.taskmanager.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fic.taskmanager.R;
import com.fic.taskmanager.data.appDatabase;
import com.fic.taskmanager.data.task;
import com.fic.taskmanager.data.history;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class taskFormActivity extends AppCompatActivity {

    private EditText etTitle, etDescription;
    private Button btnSave;
    private appDatabase db;
    private int editingTaskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);
        db = appDatabase.getDatabase(this);

        // Detectar si venimos a editar
        if (getIntent().hasExtra("task_id")) {
            editingTaskId = getIntent().getIntExtra("task_id", -1);
            etTitle.setText(getIntent().getStringExtra("title"));
            etDescription.setText(getIntent().getStringExtra("description"));
            btnSave.setText("Actualizar tarea");
        }

        btnSave.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

            if (editingTaskId == -1) {
                // Insertar nueva
                task newTask = new task(title, desc, date, false);
                db.taskDao().insertTask(newTask);

                history log = new history("insert_task", date, "Creó: " + title);
                db.historyDao().insertHistory(log);
            } else {
                // Actualizar existente
                task updatedTask = new task(title, desc, date, false);
                updatedTask.setId(editingTaskId);
                db.taskDao().updateTask(updatedTask);

                history log = new history("update_task", date, "Actualizó: " + title);
                db.historyDao().insertHistory(log);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
