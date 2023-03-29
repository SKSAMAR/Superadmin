package com.fintech.prepe.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.prepe.data.network.responses.OperatorResponse;
import com.fintech.prepe.databinding.FastagOpLayoutBinding;
import com.fintech.prepe.listeners.OperatorListener;

import java.util.ArrayList;
import java.util.List;

public class APIOperatorAdapter extends RecyclerView.Adapter<APIOperatorAdapter.APIOperatorViewModel> implements Filterable {

        OperatorListener listener;
        List<OperatorResponse.OperatorData> arrayListFilter;
        List<OperatorResponse.OperatorData> arrayList;
        LayoutInflater inflater;


        public APIOperatorAdapter(List<OperatorResponse.OperatorData> list, OperatorListener listener){
            this.arrayList = list;
            this.listener = listener;
            this.arrayListFilter = new ArrayList<>();
            this.arrayListFilter =arrayList;
        }

        @NonNull
        @Override
        public APIOperatorViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(inflater == null){
                inflater = LayoutInflater.from(parent.getContext());
            }
            FastagOpLayoutBinding binding = FastagOpLayoutBinding.inflate(inflater);
            return new APIOperatorViewModel(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull APIOperatorViewModel holder, int position) {
            holder.binding.setOpData(arrayListFilter.get(position));
            holder.binding.getRoot().setOnClickListener(v -> listener.myOperatorClicks(v, arrayListFilter.get(position)));
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
                        ArrayList<OperatorResponse.OperatorData> filterList = new ArrayList<>();
                        for(OperatorResponse.OperatorData p : arrayList){
                            if(p.getName().toLowerCase().contains(character.toLowerCase())){
                                filterList.add(p);
                            }
                            else if(p.getDisplayname().toLowerCase().contains(character.toLowerCase())){
                                filterList.add(p);
                            }
                            else if(p.getCategory().toLowerCase().contains(character.toLowerCase())){
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
                    arrayListFilter = (ArrayList<OperatorResponse.OperatorData>)results.values;
                    notifyDataSetChanged();
                }
            };
        }

        public static class APIOperatorViewModel extends RecyclerView.ViewHolder{
            FastagOpLayoutBinding binding;
            public APIOperatorViewModel(@NonNull FastagOpLayoutBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
