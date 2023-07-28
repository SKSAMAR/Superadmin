package com.fintech.superadmin.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.data.model.BankModel;
import com.fintech.superadmin.data.model.EkoBankModel;
import com.fintech.superadmin.databinding.BankListLayoutBinding;
import com.fintech.superadmin.databinding.EkoBankListLayoutBinding;
import com.fintech.superadmin.listeners.BanksListener;
import com.fintech.superadmin.listeners.EkoBanksListener;

import java.util.ArrayList;

public class EkoBanksAdapter extends RecyclerView.Adapter<EkoBanksAdapter.BanksViewHolder> implements Filterable {

    ArrayList<EkoBankModel> arrayListFilter;
    ArrayList<EkoBankModel> arrayList = new ArrayList<>();

    EkoBanksListener listener;

    public EkoBanksAdapter(ArrayList<EkoBankModel> arrayList, EkoBanksListener listener){
        this.arrayList = arrayList;
        this.listener = listener;
        this.arrayListFilter = new ArrayList<>();
        this.arrayListFilter =arrayList;
    }


    @NonNull
    @Override
    public BanksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EkoBankListLayoutBinding binding = EkoBankListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BanksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BanksViewHolder holder, int position) {
        holder.binding.setBankModel(arrayListFilter.get(position));
        holder.binding.getRoot().setOnClickListener(v -> {
            listener.selectedBanks(v,arrayListFilter.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if(arrayListFilter == null) {
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
                    ArrayList<EkoBankModel> filterList = new ArrayList<>();
                    for(EkoBankModel p : arrayList){
                        if(p.getBANKNAME().toLowerCase().contains(character.toLowerCase())){
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
                arrayListFilter = (ArrayList<EkoBankModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class BanksViewHolder extends RecyclerView.ViewHolder{
        EkoBankListLayoutBinding binding;
        public BanksViewHolder(@NonNull EkoBankListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
