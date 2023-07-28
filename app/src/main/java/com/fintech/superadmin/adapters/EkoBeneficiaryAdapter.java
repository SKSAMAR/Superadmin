package com.fintech.superadmin.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.data.eko.RecipientListItem;
import com.fintech.superadmin.data.network.responses.BeneficiaryBank;
import com.fintech.superadmin.databinding.BeneficiaryListLayoutBinding;
import com.fintech.superadmin.databinding.EkoBeneficiaryListLayoutBinding;
import com.fintech.superadmin.listeners.BeneficiaryClickListener;
import com.fintech.superadmin.listeners.EkoBeneficiaryClickListener;

import java.util.ArrayList;
import java.util.List;

public class EkoBeneficiaryAdapter extends RecyclerView.Adapter<EkoBeneficiaryAdapter.BeneficiaryViewHolder> implements Filterable {
    List<RecipientListItem> list;
    List<RecipientListItem> filterList;
    EkoBeneficiaryClickListener listener;

    public EkoBeneficiaryAdapter(List<RecipientListItem> list, EkoBeneficiaryClickListener listener) {
        this.list = list;
        this.filterList = new ArrayList<>();
        this.filterList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BeneficiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EkoBeneficiaryListLayoutBinding binding = EkoBeneficiaryListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BeneficiaryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficiaryViewHolder holder, int position) {
        holder.binding.setBeneficiaryModel(filterList.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.onItemClicked(v, filterList.get(position)));

        holder.binding.deleteBeneficiary.setOnClickListener(v -> listener.onMoreClickListener(v, filterList.get(position)));
    }

    @Override
    public int getItemCount() {
        if (filterList == null) {
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
                if (character.isEmpty()) {
                    filterList = list;
                } else {
                    List<RecipientListItem> myFilterList = new ArrayList<>();
                    for (RecipientListItem p : list) {
                        try {
                            if (p.getBank().toLowerCase().contains(character.toLowerCase())) {
                                myFilterList.add(p);
                            } else if (p.getRecipientName().toLowerCase().contains(character.toLowerCase())) {
                                myFilterList.add(p);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
                filterList = (ArrayList<RecipientListItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class BeneficiaryViewHolder extends RecyclerView.ViewHolder {
        EkoBeneficiaryListLayoutBinding binding;

        public BeneficiaryViewHolder(@NonNull EkoBeneficiaryListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
