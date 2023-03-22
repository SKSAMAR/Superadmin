package com.fintech.petoindia.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.petoindia.data.browseplan.Plan;
import com.fintech.petoindia.databinding.ROfferLayoutBinding;
import com.fintech.petoindia.listeners.BrowsePlanListener;
import com.fintech.petoindia.listeners.RegularClick;

import java.util.ArrayList;
import java.util.List;

public class ROfferAdapter extends RecyclerView.Adapter<ROfferAdapter.ROfferViewHolder> implements Filterable {

    LayoutInflater inflater;
    List<Plan> listFilter;
    List<Plan> list;
    RegularClick listener;
    BrowsePlanListener browseListener;


    public ROfferAdapter(List<Plan> list, RegularClick listener, BrowsePlanListener browseListener){
        this.list = list;
        this.listener = listener;
        this.listFilter = new ArrayList<>();
        this.listFilter =list;
        this.browseListener= browseListener;
    }

    @NonNull
    @Override
    public ROfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        ROfferLayoutBinding binding = ROfferLayoutBinding.inflate(inflater);
        return new ROfferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ROfferViewHolder holder, int position) {
        holder.binding.setROfferDataModel(listFilter.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.onClickItem(v, listFilter.get(position).rs));
    }

    @Override
    public int getItemCount() {
        if(listFilter == null){
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
                    listFilter = list;
                }
                else{
                    ArrayList<Plan> filterList = new ArrayList<>();
                    for(Plan p : list){
                        if(p.rs.toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.validity.toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                    }
                    listFilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFilter = (List<Plan>)results.values;
                notifyDataSetChanged();
                if(listFilter.isEmpty()){
                    browseListener.notFoundListener(true);
                }
                else{
                    browseListener.notFoundListener(false);
                }
            }
        };
    }



    public static class ROfferViewHolder extends RecyclerView.ViewHolder{
        ROfferLayoutBinding binding;
        public ROfferViewHolder(@NonNull ROfferLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
