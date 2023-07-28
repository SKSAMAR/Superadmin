package com.fintech.superadmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.data.eko.EkoDtmTransactionHistory;
import com.fintech.superadmin.data.network.responses.BeneficiaryHistoryResponse;
import com.fintech.superadmin.databinding.BeneficiaryHistoryLayoutBinding;
import com.fintech.superadmin.databinding.EkoBeneficiaryHistoryLayoutBinding;
import com.fintech.superadmin.listeners.BeneficiaryHistoryListener;

import java.util.List;

public class EkoSimpleBeneficiaryHistoryAdapter extends RecyclerView.Adapter<EkoSimpleBeneficiaryHistoryAdapter.MyViewHolder> {

    List<EkoDtmTransactionHistory> filterList;
    List<EkoDtmTransactionHistory> list;
    BeneficiaryHistoryListener<EkoDtmTransactionHistory> listener;
    public EkoSimpleBeneficiaryHistoryAdapter(List<EkoDtmTransactionHistory> list, BeneficiaryHistoryListener<EkoDtmTransactionHistory> listener){
        this.filterList = list;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EkoBeneficiaryHistoryLayoutBinding binding = EkoBeneficiaryHistoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setHistoryModelResponse(filterList.get(position));
        try {
            if(filterList.get(position).getData()!=null && (filterList.get(position).getStatus().equals(0) || filterList.get(position).getResponseStatusId().equals(0))){
//                holder.binding.clickUpdate.setVisibility(View.GONE);
                holder.binding.getRoot().setOnClickListener(v -> listener.clickOnMoreInfo(v, filterList.get(position)));
            }
            else{
//                holder.binding.clickUpdate.setVisibility(View.VISIBLE);
            }
//            holder.binding.clickUpdate.setOnClickListener(v -> listener.clickOnUpdateInfo(v, filterList.get(position)));
//            holder.binding.refundButton.setOnClickListener(v -> listener.clickOnRefund(v, filterList.get(position)));
        }catch (Exception e){
            e.printStackTrace();
//            holder.binding.clickUpdate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(filterList==null){
            return 0;
        }
        return filterList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        EkoBeneficiaryHistoryLayoutBinding binding;
        public MyViewHolder(@NonNull EkoBeneficiaryHistoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
