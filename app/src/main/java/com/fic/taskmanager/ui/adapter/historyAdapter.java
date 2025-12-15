package com.fic.taskmanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.fic.taskmanager.R;
import com.fic.taskmanager.data.history;

import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {

    private List<history> historyList;

    public historyAdapter(List<history> historyList) {
        this.historyList = historyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAction, tvDate, tvDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAction = itemView.findViewById(R.id.tvAction);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDetails = itemView.findViewById(R.id.tvDetails);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        history h = historyList.get(position);
        holder.tvAction.setText("Acción: " + h.action);
        holder.tvDate.setText("Fecha: " + h.created_at);
        holder.tvDetails.setText("Detalles: " + h.details);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void updateData(List<history> newData) {
        this.historyList = newData;
        notifyDataSetChanged();
    }
}
