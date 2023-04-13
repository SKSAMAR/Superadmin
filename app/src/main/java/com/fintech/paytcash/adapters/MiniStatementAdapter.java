package com.fintech.paytcash.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.paytcash.data.network.responses.MiniStatementData;
import com.fintech.paytcash.databinding.MiniStatementLayoutBinding;

import java.util.List;

public class MiniStatementAdapter extends RecyclerView.Adapter<MiniStatementAdapter.StatementViewHolder> {

    List<MiniStatementData> dataList;
    LayoutInflater inflater;

    public MiniStatementAdapter(List<MiniStatementData> dataList){
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public StatementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        MiniStatementLayoutBinding binding = MiniStatementLayoutBinding.inflate(inflater);
        return new StatementViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StatementViewHolder holder, int position) {
        holder.binding.setMinSatementModel(dataList.get(position));
        if(dataList.get(position).getTxnType().equals("Cr")){
            holder.binding.givenCrDr.setTextColor(Color.GREEN);
        }
        else{
            holder.binding.givenCrDr.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        if(dataList==null){
            return 0;
        }
        return dataList.size();
    }

    public static class StatementViewHolder extends RecyclerView.ViewHolder{
        MiniStatementLayoutBinding binding;
        public StatementViewHolder(@NonNull MiniStatementLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
