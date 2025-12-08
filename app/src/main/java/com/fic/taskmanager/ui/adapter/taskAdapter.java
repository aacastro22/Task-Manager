package com.fic.taskmanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fic.taskmanager.R;
import com.fic.taskmanager.data.task;

import java.util.List;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.taskViewHolder> {

    private List<task> taskList;

    public taskAdapter(List<task> taskList) {
        this.taskList = taskList;
    }

    public static class taskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvCreatedAt;

        public taskViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
        }
    }

    @Override
    public taskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new taskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(taskViewHolder holder, int position) {
        task task = taskList.get(position);
        holder.tvTitle.setText(task.getTask_title());
        holder.tvDescription.setText(task.getTask_description());
        holder.tvCreatedAt.setText("Creado el: " + task.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateData(List<task> newtasks) {
        this.taskList = newtasks;
        notifyDataSetChanged();
    }
}
