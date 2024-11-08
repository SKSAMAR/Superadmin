package com.fintech.superadmin.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.data.model.TicketHistoryModel;
import com.fintech.superadmin.databinding.TicketHistoryDesignBinding;

import java.util.List;

public class TicketHistoryAdapter extends RecyclerView.Adapter<TicketHistoryAdapter.TicketHistoryViewHolder>{

    List<TicketHistoryModel> list;

    public TicketHistoryAdapter(List<TicketHistoryModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public TicketHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TicketHistoryDesignBinding binding = TicketHistoryDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new TicketHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHistoryViewHolder holder, int position) {
        holder.binding.setTicketModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    static class TicketHistoryViewHolder extends RecyclerView.ViewHolder{
        TicketHistoryDesignBinding binding;
        public TicketHistoryViewHolder(@NonNull TicketHistoryDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
