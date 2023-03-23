package com.fintech.payware.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.payware.R;
import com.fintech.payware.data.model.PDMTHistoryModel;
import com.fintech.payware.databinding.PdmtHistoryLayoutBinding;
import com.fintech.payware.deer_listener.MasterListener;
import com.fintech.payware.deer_listener.MyRecyclerListener;

import java.util.ArrayList;
import java.util.List;

public class CFPDMTHistoryAdapter extends RecyclerView.Adapter<CFPDMTHistoryAdapter.MyViewHolder> implements Filterable {
    MyRecyclerListener<PDMTHistoryModel> myRecyclerListener;
    List<PDMTHistoryModel> list;
    MasterListener listener;
    List<PDMTHistoryModel> filterList;
    LayoutInflater inflater;

    public CFPDMTHistoryAdapter(@Nullable List<PDMTHistoryModel> list, MasterListener listener, MyRecyclerListener<PDMTHistoryModel> myRecyclerListener){
        this.list = list;
        this.filterList = new ArrayList<>();
        this.filterList = list;
        this.myRecyclerListener = myRecyclerListener;
        this.listener = listener;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        PdmtHistoryLayoutBinding binding = PdmtHistoryLayoutBinding.inflate(inflater);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setPDmtHistory(filterList.get(position));

        try {
            String status = filterList.get(position).getSTATUS().toLowerCase();
            if(status.equals("accepted")){
                holder.binding.status.setImageResource(R.drawable.ic_pending);
            }
            else if(status.equals("success")){
                holder.binding.status.setImageResource(R.drawable.ic_baseline_verified_symb);
            }
            else{
                holder.binding.status.setImageResource(R.drawable.ic_unverified_symb);
            }
        }catch (NullPointerException e){
            holder.binding.status.setImageResource(R.drawable.ic_unverified_symb);
        }


        holder.binding.deleteBeneficiary.setOnClickListener(v -> myRecyclerListener.onItemPassed(v, filterList.get(position)));
        holder.binding.wholeBody.setOnClickListener(v-> listener.onTaskPerformer(list.get(position), "root"));

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setGivenList(List<PDMTHistoryModel> sentList){
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
                    List<PDMTHistoryModel> myFilterList = new ArrayList<>();
                    for(PDMTHistoryModel p : list){
                        if(p.getACCOUNT().toLowerCase().contains(character.toLowerCase())){
                            myFilterList.add(p);
                        }
                        else if(p.getFILTER_DATE().toLowerCase().contains(character.toLowerCase())){
                            myFilterList.add(p);
                        }
                        else if(p.getAMOUNT().toLowerCase().contains(character.toLowerCase())){
                            myFilterList.add(p);
                        }
                    }
                    filterList = myFilterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList = (ArrayList<PDMTHistoryModel>)results.values;
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
        PdmtHistoryLayoutBinding binding;
        public MyViewHolder(@NonNull PdmtHistoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
