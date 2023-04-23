package com.fintech.superadmin.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.data.model.AEPSBanksModel;
import com.fintech.superadmin.databinding.AepsBankListLayoutBinding;
import com.fintech.superadmin.listeners.AepsBankListener;

import java.util.ArrayList;
import java.util.List;


public class AEPSBankAdapter extends RecyclerView.Adapter<AEPSBankAdapter.AEPSViewHolder> implements Filterable {
    
    LayoutInflater layoutInflater;
    AepsBankListener listener;


    List<AEPSBanksModel> listFilter;
    List<AEPSBanksModel> arrayList = new ArrayList<>();

    public AEPSBankAdapter(List<AEPSBanksModel> list, AepsBankListener listener){
        this.arrayList = list;
        this.listener = listener;
        this.listFilter = new ArrayList<>();
        this.listFilter =arrayList;
    }

    @NonNull
    @Override
    public AEPSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AepsBankListLayoutBinding binding = AepsBankListLayoutBinding.inflate(layoutInflater);
        return new AEPSViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AEPSViewHolder holder, int position) {
        holder.binding.setBankListModel(listFilter.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.selectedAepsBanks(v, listFilter.get(position)));
    }

    @Override
    public int getItemCount() {
        if(listFilter==null) {
            return 0;
        }
        return listFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String character = constraint.toString();
                if(character.isEmpty()){
                    listFilter = arrayList;
                }
                else{
                    List<AEPSBanksModel> filterList = new ArrayList<>();
                    for(AEPSBanksModel p : arrayList){
                        if(p.getBankname().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                    }
                    listFilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFilter = (List<AEPSBanksModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class AEPSViewHolder extends RecyclerView.ViewHolder{

        AepsBankListLayoutBinding binding;
        public AEPSViewHolder(@NonNull AepsBankListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
