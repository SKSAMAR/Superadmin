package com.fintech.superadmin.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.R;
import com.fintech.superadmin.data.deer_response.CFPDBeneficiary;
import com.fintech.superadmin.databinding.CfbpdListLayoutBinding;
import com.fintech.superadmin.deer_listener.MasterListener;
import com.fintech.superadmin.deer_listener.MyRecyclerListener;

import java.util.ArrayList;
import java.util.List;

public class CFBPDAdapter extends RecyclerView.Adapter<CFBPDAdapter.MyViewHolder> implements Filterable {

    MyRecyclerListener<CFPDBeneficiary> myRecyclerListener;
    MasterListener listener;
    List<CFPDBeneficiary> list;
    List<CFPDBeneficiary> filterList;
    LayoutInflater inflater;

    public CFBPDAdapter(@Nullable List<CFPDBeneficiary> list, MasterListener listener, MyRecyclerListener<CFPDBeneficiary> myRecyclerListener){
        this.list = list;
        this.filterList = new ArrayList<>();
        this.filterList = list;
        this.listener = listener;
        this.myRecyclerListener = myRecyclerListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        CfbpdListLayoutBinding binding = CfbpdListLayoutBinding.inflate(inflater);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setBeneficiaryModel(filterList.get(position));

        if(filterList.get(position).getVerified()){
            holder.binding.verification.setImageResource(R.drawable.ic_baseline_verified_symb);
        }
        else{
            holder.binding.verification.setImageResource(R.drawable.ic_unverified_symb);
        }

        holder.binding.deleteBeneficiary.setOnClickListener(v -> {
              myRecyclerListener.onItemPassed(v, filterList.get(position));
        });
        holder.binding.wholeBody.setOnClickListener(v->{
            listener.onTaskPerformer(list.get(position), "root");
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setGivenList(List<CFPDBeneficiary> sentList){
        this.list = sentList;
        this.filterList = new ArrayList<>();
        this.filterList = list;
        if(filterList==null || filterList.isEmpty()){
            listener.onErrorReceived("No Records");
        }
        else{
            listener.onErrorReceived("Records Added");
        }
        notifyDataSetChanged();
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
                    List<CFPDBeneficiary> myFilterList = new ArrayList<>();
                    for(CFPDBeneficiary p : list){
                        if(p.getACCOUNT().toLowerCase().contains(character.toLowerCase())){
                            myFilterList.add(p);
                        }
                        else if(p.getNAME().toLowerCase().contains(character.toLowerCase())){
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
                filterList = (ArrayList<CFPDBeneficiary>)results.values;
                if(filterList==null || filterList.isEmpty()){
                    listener.onErrorReceived("No Records");
                }
                else{
                    listener.onErrorReceived("Records Filtered");
                }
                notifyDataSetChanged();
            }
        };
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        CfbpdListLayoutBinding binding;
        public MyViewHolder(@NonNull CfbpdListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
