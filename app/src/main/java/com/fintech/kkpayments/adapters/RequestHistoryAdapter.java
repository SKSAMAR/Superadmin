package com.fintech.kkpayments.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.kkpayments.data_model.request.RequestedHistoryModel;
import com.fintech.kkpayments.databinding.RequestedHistoryDesignBinding;

import java.util.List;

public class RequestHistoryAdapter extends RecyclerView.Adapter<RequestHistoryAdapter.RequestHistoryViewHolder>{

    List<RequestedHistoryModel> list;

    public RequestHistoryAdapter(List<RequestedHistoryModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public RequestHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RequestedHistoryDesignBinding binding = RequestedHistoryDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RequestHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHistoryViewHolder holder, int position) {
        holder.binding.setRequestHistoryModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    static class RequestHistoryViewHolder extends RecyclerView.ViewHolder{
        RequestedHistoryDesignBinding binding;
        public RequestHistoryViewHolder(@NonNull RequestedHistoryDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
