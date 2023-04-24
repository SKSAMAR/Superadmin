package com.fintech.superadmin.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.data.ROfferPlan;
import com.fintech.superadmin.databinding.DynamicRofferLayoutBinding;
import com.fintech.superadmin.listeners.BrowsePlanListener;
import com.fintech.superadmin.listeners.RegularClick;

import java.util.ArrayList;
import java.util.List;

public class DynamicROfferAdapter extends RecyclerView.Adapter<DynamicROfferAdapter.ROfferDynamicViewHolder> implements Filterable {

    LayoutInflater inflater;
    List<ROfferPlan> listFilter;
    List<ROfferPlan> list;
    RegularClick listener;
    BrowsePlanListener browseListener;


    public DynamicROfferAdapter(List<ROfferPlan> list, RegularClick listener, BrowsePlanListener browseListener) {
        this.list = list;
        this.listener = listener;
        this.listFilter = new ArrayList<>();
        this.listFilter = list;
        this.browseListener = browseListener;
    }

    @NonNull
    @Override
    public ROfferDynamicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        DynamicRofferLayoutBinding binding = DynamicRofferLayoutBinding.inflate(inflater);
        return new ROfferDynamicViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ROfferDynamicViewHolder holder, int position) {
        holder.binding.setROfferDataModel(listFilter.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.onClickItem(v, listFilter.get(position).getRS()));
    }

    @Override
    public int getItemCount() {
        if (listFilter == null) {
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
                if (character.isEmpty()) {
                    listFilter = list;
                } else {
                    ArrayList<ROfferPlan> filterList = new ArrayList<>();
                    for (ROfferPlan p : list) {
                        if (p.getRS().toLowerCase().contains(character.toLowerCase())) {
                            filterList.add(p);
                        } else if (p.getDESC().toLowerCase().contains(character.toLowerCase())) {
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
                listFilter = (List<ROfferPlan>) results.values;
                notifyDataSetChanged();
                if (listFilter.isEmpty()) {
                    browseListener.notFoundListener(true);
                } else {
                    browseListener.notFoundListener(false);
                }
            }
        };
    }


    public static class ROfferDynamicViewHolder extends RecyclerView.ViewHolder {
        DynamicRofferLayoutBinding binding;

        public ROfferDynamicViewHolder(@NonNull DynamicRofferLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
