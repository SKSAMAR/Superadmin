package com.fintech.petoindia.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.petoindia.data.network.responses.RegularHistoryResponse;
import com.fintech.petoindia.databinding.RegularHistoryDesignBinding;
import com.fintech.petoindia.listeners.RegularHistoryListener;

import java.util.ArrayList;
import java.util.List;

public class RegularHistoryAdapter extends RecyclerView.Adapter<RegularHistoryAdapter.HistViewHolder> implements Filterable {


    RegularHistoryListener listener;
    List<RegularHistoryResponse.EveryDayData> arrayListFilter;
    List<RegularHistoryResponse.EveryDayData> arrayList;
    LayoutInflater inflater;


    public RegularHistoryAdapter(List<RegularHistoryResponse.EveryDayData> list, RegularHistoryListener listener){
        this.arrayList = list;
        this.listener = listener;
        this.arrayListFilter = new ArrayList<>();
        this.arrayListFilter =arrayList;
    }
    

    @NonNull
    @Override
    public HistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        RegularHistoryDesignBinding binding = RegularHistoryDesignBinding.inflate(inflater);
        return new HistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistViewHolder holder, int position) {
        holder.binding.setHistData(arrayListFilter.get(position));
        holder.binding.historyClick.setOnClickListener(v -> listener.onCheckStatusData(arrayListFilter.get(position)));
        listener.disableListener(holder.binding, arrayListFilter.get(position));
        holder.binding.claimRefund.setOnClickListener(v -> listener.onRefundClick(arrayListFilter.get(position)));
    }
    
    
    @Override
    public int getItemCount() {
        if(arrayListFilter == null){
            return 0;
        }
        return arrayListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String character = constraint.toString();
                if(character.isEmpty()){
                    arrayListFilter = arrayList;
                }
                else{
                    ArrayList<RegularHistoryResponse.EveryDayData> filterList = new ArrayList<>();
                    for(RegularHistoryResponse.EveryDayData p : arrayList){
                        if(p.getAmount().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getNumber().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getStatus().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getReference().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                    }
                    arrayListFilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayListFilter = (ArrayList<RegularHistoryResponse.EveryDayData>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class HistViewHolder extends RecyclerView.ViewHolder{
        RegularHistoryDesignBinding binding;
        public HistViewHolder(@NonNull RegularHistoryDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
