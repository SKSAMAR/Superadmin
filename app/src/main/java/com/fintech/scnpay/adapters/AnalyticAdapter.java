package com.fintech.scnpay.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.scnpay.data.network.responses.AnalyticsResponseModel;
import com.fintech.scnpay.databinding.AnalyticDesignLayoutBinding;
import com.fintech.scnpay.listeners.AnalyticOperationListener;

import java.util.ArrayList;
import java.util.List;

public class AnalyticAdapter extends RecyclerView.Adapter<AnalyticAdapter.AnalyticViewHolder> {

    List<AnalyticsResponseModel> analyticsList;
    AnalyticOperationListener listener;
    LayoutInflater inflater;

    public AnalyticAdapter(List<AnalyticsResponseModel> analyticsList, AnalyticOperationListener listener) {
        this.analyticsList = analyticsList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public AnalyticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        AnalyticDesignLayoutBinding binding = AnalyticDesignLayoutBinding.inflate(inflater);
        return new AnalyticViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalyticViewHolder holder, int position) {

        holder.binding.setAnalyticDataModel(analyticsList.get(position));
        holder.binding.checkDetails.setOnClickListener(v -> listener.checkMyDetailsOf(v, analyticsList.get(position)));
        //holder.binding.updateButton.setOnClickListener(v -> listener.updateMyDetailsOf(v, analyticsList.get(position)));
        holder.binding.updateButton.setVisibility(View.GONE);
        listener.observerData(holder.binding.updateButton, analyticsList.get(position));

        try {
            if (analyticsList.get(position).getStatus().trim().equalsIgnoreCase("pending")){
                holder.binding.updateButton.setVisibility(View.VISIBLE);
            }else{
                holder.binding.updateButton.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
            holder.binding.updateButton.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        if (analyticsList == null) {
            return 0;
        }
        return analyticsList.size();
    }


    public void setAllData(List<AnalyticsResponseModel> list) {
        this.analyticsList.addAll(list);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetTheList() {
        analyticsList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class AnalyticViewHolder extends RecyclerView.ViewHolder {
        AnalyticDesignLayoutBinding binding;

        public AnalyticViewHolder(@NonNull AnalyticDesignLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public String getLastPositionId() {
        if (analyticsList.size() == 0) {
            return "0";
        } else {
            return String.valueOf(analyticsList.get(analyticsList.size() - 1).getId());
        }
    }


}
