package com.fic.taskmanager.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fic.taskmanager.R;
import com.fic.taskmanager.data.appDatabase;
import com.fic.taskmanager.data.task;
import com.fic.taskmanager.data.history;
import com.fic.taskmanager.ui.taskFormActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder> {

    private List<task> taskList;
    private Context context;

    public taskAdapter(List<task> taskList) {
        this.taskList = taskList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        Button btnEdit, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        task currentTask = taskList.get(position);
        holder.tvTitle.setText(currentTask.getTask_title());
        holder.tvDescription.setText(currentTask.getTask_description());

        // 🟢 Editar tarea
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, taskFormActivity.class);
            intent.putExtra("task_id", currentTask.getId());
            intent.putExtra("title", currentTask.getTask_title());
            intent.putExtra("description", currentTask.getTask_description());
            context.startActivity(intent);
        });

        // 🔴 Eliminar tarea
        holder.btnDelete.setOnClickListener(v -> {
            appDatabase db = appDatabase.getDatabase(context);
            Executors.newSingleThreadExecutor().execute(() -> {
                db.taskDao().deleteTask(currentTask);

                // Registrar en historial
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
                history log = new history("delete_task", date, "Eliminó: " + currentTask.getTask_title());
                db.historyDao().insertHistory(log);
            });

            // Eliminar del RecyclerView
            taskList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateData(List<task> newData) {
        this.taskList = newData;
        notifyDataSetChanged();
    }
}
