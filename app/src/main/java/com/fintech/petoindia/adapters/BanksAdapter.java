package com.fintech.petoindia.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fintech.petoindia.data.model.BankModel;
import com.fintech.petoindia.databinding.BankListLayoutBinding;
import com.fintech.petoindia.listeners.BanksListener;
import java.util.ArrayList;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BanksViewHolder> implements Filterable {

    ArrayList<BankModel> arrayListFilter;
    ArrayList<BankModel> arrayList = new ArrayList<>();

    BanksListener listener;

    public BanksAdapter(ArrayList<BankModel> arrayList, BanksListener listener){
        this.arrayList = arrayList;
        this.listener = listener;
        this.arrayListFilter = new ArrayList<>();
        this.arrayListFilter =arrayList;
    }


    @NonNull
    @Override
    public BanksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BankListLayoutBinding binding = BankListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
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
                    ArrayList<BankModel> filterList = new ArrayList<>();
                    for(BankModel p : arrayList){
                        if(p.getBankname().toLowerCase().contains(character.toLowerCase())){
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
                arrayListFilter = (ArrayList<BankModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class BanksViewHolder extends RecyclerView.ViewHolder{
        BankListLayoutBinding binding;
        public BanksViewHolder(@NonNull BankListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
