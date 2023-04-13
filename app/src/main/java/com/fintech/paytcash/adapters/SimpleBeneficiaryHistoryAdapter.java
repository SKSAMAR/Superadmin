package com.fintech.paytcash.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.paytcash.data.network.responses.BeneficiaryHistoryResponse;
import com.fintech.paytcash.databinding.BeneficiaryHistoryLayoutBinding;
import com.fintech.paytcash.listeners.BeneficiaryHistoryListener;

import java.util.List;

public class SimpleBeneficiaryHistoryAdapter extends RecyclerView.Adapter<SimpleBeneficiaryHistoryAdapter.MyViewHolder> {

    List<BeneficiaryHistoryResponse> filterList;
    List<BeneficiaryHistoryResponse> list;
    BeneficiaryHistoryListener listener;
    public SimpleBeneficiaryHistoryAdapter(List<BeneficiaryHistoryResponse> list, BeneficiaryHistoryListener listener){
        this.filterList = list;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BeneficiaryHistoryLayoutBinding binding = BeneficiaryHistoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setHistoryModelResponse(filterList.get(position));
        if(filterList.get(position).getData()!=null && (filterList.get(position).getData().isStatus() || filterList.get(position).getData().response_code.equals(1))){
            holder.binding.clickUpdate.setVisibility(View.GONE);
            holder.binding.getRoot().setOnClickListener(v -> listener.clickOnMoreInfo(v, filterList.get(position)));
        }
        else{
            holder.binding.clickUpdate.setVisibility(View.VISIBLE);
        }

        holder.binding.clickUpdate.setOnClickListener(v -> listener.clickOnUpdateInfo(v, filterList.get(position)));

        holder.binding.refundButton.setOnClickListener(v -> listener.clickOnRefund(v, filterList.get(position)));


    }

    @Override
    public int getItemCount() {
        if(filterList==null){
            return 0;
        }
        return filterList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        BeneficiaryHistoryLayoutBinding binding;
        public MyViewHolder(@NonNull BeneficiaryHistoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
