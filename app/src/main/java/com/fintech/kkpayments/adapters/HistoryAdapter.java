package com.fintech.kkpayments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.data.model.HistoryModel;
import com.fintech.kkpayments.databinding.HistoryListLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    LayoutInflater layoutInflater;
    List<HistoryModel> historyModelsListFilter;

    public HistoryAdapter(List<HistoryModel> historyModelsList){
        this.historyModelsListFilter = new ArrayList<>();
        this.historyModelsListFilter = historyModelsList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater  = LayoutInflater.from(parent.getContext());
        }
        HistoryListLayoutBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.history_list_layout, parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.binding.setMyHistoryModel(historyModelsListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        if(historyModelsListFilter == null){
            return 0;
        }
        return historyModelsListFilter.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        public HistoryListLayoutBinding binding;
        public HistoryViewHolder(@NonNull HistoryListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetTheList(){
        historyModelsListFilter = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setAllData(List<HistoryModel> historyModelsListFilter){
        this.historyModelsListFilter.addAll(historyModelsListFilter);
    }

    public String getLastPositionId(){
        if(historyModelsListFilter.size() == 0) {
            return "0";
        }
        else{
            return String.valueOf(historyModelsListFilter.get(historyModelsListFilter.size() - 1).getId());
        }
    }

}
