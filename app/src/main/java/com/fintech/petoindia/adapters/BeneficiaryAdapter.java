package com.fintech.petoindia.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.petoindia.data.network.responses.BeneficiaryBank;
import com.fintech.petoindia.databinding.BeneficiaryListLayoutBinding;
import com.fintech.petoindia.listeners.BeneficiaryClickListener;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder> implements Filterable {
    List<BeneficiaryBank> list;
    List<BeneficiaryBank> filterList;
    BeneficiaryClickListener listener;

    public BeneficiaryAdapter(List<BeneficiaryBank> list, BeneficiaryClickListener listener){
        this.list = list;
        this.filterList = new ArrayList<>();
        this.filterList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BeneficiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BeneficiaryListLayoutBinding binding = BeneficiaryListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BeneficiaryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficiaryViewHolder holder, int position) {
        holder.binding.setBeneficiaryModel(filterList.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.onItemClicked(v,filterList.get(position)));

        holder.binding.deleteBeneficiary.setOnClickListener(v -> listener.onMoreClickListener(v, filterList.get(position)));
    }

    @Override
    public int getItemCount() {
        if(filterList==null){
            return 0;
        }
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String character = constraint.toString();
                if(character.isEmpty()){
                    filterList = list;
                }
                else{
                    List<BeneficiaryBank> myFilterList = new ArrayList<>();
                    for(BeneficiaryBank p : list){
                        if(p.bankname.toLowerCase().contains(character.toLowerCase())){
                            myFilterList.add(p);
                        }
                        else if(p.name.toLowerCase().contains(character.toLowerCase())){
                            myFilterList.add(p);
                        }
                    }
                    filterList = myFilterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList = (ArrayList<BeneficiaryBank>)results.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class BeneficiaryViewHolder extends RecyclerView.ViewHolder{
        BeneficiaryListLayoutBinding binding;
        public BeneficiaryViewHolder(@NonNull BeneficiaryListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
