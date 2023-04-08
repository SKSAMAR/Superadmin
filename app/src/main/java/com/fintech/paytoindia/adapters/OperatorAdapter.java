package com.fintech.paytoindia.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.paytoindia.R;
import com.fintech.paytoindia.data.model.OperatorModel;
import com.fintech.paytoindia.databinding.OperatorListLayoutBinding;
import com.fintech.paytoindia.listeners.OperatorListener;

import java.util.ArrayList;
import java.util.List;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.OperatorViewModel> implements Filterable {

    OperatorListener listener;

    List<OperatorModel> arrayListFilter;
    List<OperatorModel> arrayList;


    public OperatorAdapter(List<OperatorModel> list, OperatorListener listener){
        this.arrayList = list;
        this.listener = listener;
        this.arrayListFilter = new ArrayList<>();
        this.arrayListFilter =arrayList;
    }

    @NonNull
    @Override
    public OperatorViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OperatorListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.operator_list_layout, parent, false);
        return new OperatorViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OperatorViewModel holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.setOperatorModel(arrayListFilter.get(position));
        Log.d("OperatorImages", arrayListFilter.get(position).getLogo());
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
                    ArrayList<OperatorModel> filterList = new ArrayList<>();
                    for(OperatorModel p : arrayList){
                        if(p.getName().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getName().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                    }
                    arrayListFilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayListFilter;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayListFilter = (ArrayList<OperatorModel>)results.values;
                notifyDataSetChanged();
                if(arrayListFilter.isEmpty()){
                    listener.noOperatorFound(true);
                }
                else{
                    listener.noOperatorFound(false);
                }
            }
        };
    }

    public static class OperatorViewModel extends RecyclerView.ViewHolder{
        OperatorListLayoutBinding binding;
        public OperatorViewModel(@NonNull OperatorListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}