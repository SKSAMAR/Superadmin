package com.fintech.superadmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.data_model.DynamicData;
import com.fintech.superadmin.databinding.DynamicDatalistBinding;


import java.util.List;

public class DynamicListAdapter extends RecyclerView.Adapter<DynamicListAdapter.DynamicListViewHolder> {

    List<DynamicData> list;
    LayoutInflater inflater;
    public DynamicListAdapter(List<DynamicData> list){
        this.list = list;
    }

    @NonNull
    @Override
    public DynamicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        DynamicDatalistBinding binding = DynamicDatalistBinding.inflate(inflater, parent, false);
        return new DynamicListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicListViewHolder holder, int position) {
        if (list.get(position).value !=null && !list.get(position).value.toString().isEmpty()){
            holder.binding.setDynamicInfo(list.get(position));
            holder.binding.getRoot().setVisibility(View.VISIBLE);
        }else{
            holder.binding.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(list==null) {
            return 0;
        }
        return list.size();
    }

    public static class DynamicListViewHolder extends RecyclerView.ViewHolder{
        DynamicDatalistBinding binding;
        public DynamicListViewHolder(@NonNull DynamicDatalistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
