package com.fintech.paytcash.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.paytcash.data.network.responses.PayoutList;
import com.fintech.paytcash.databinding.PayoutListLayoutBinding;
import com.fintech.paytcash.listeners.PayoutHomeListener;

import java.util.ArrayList;
import java.util.List;
public class PaysprintPayoutAdapter extends RecyclerView.Adapter<PaysprintPayoutAdapter.PayoutAccHolder> implements Filterable {

    List<PayoutList> listFilter;
    List<PayoutList> list;
    LayoutInflater inflater;
    PayoutHomeListener listener;

    public PaysprintPayoutAdapter(List<PayoutList> list, PayoutHomeListener listener){
        this.listener = listener;
        this.list = list;
        this.listFilter = new ArrayList<>();
        this.listFilter =list;
    }


    @NonNull
    @Override
    public PayoutAccHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        PayoutListLayoutBinding binding = PayoutListLayoutBinding.inflate(inflater);
        return new PayoutAccHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PayoutAccHolder holder, int position) {
        holder.binding.setPaySprintList(listFilter.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.openBeneficiary(v, listFilter.get(position)));

        holder.binding.verifyAccount.setOnClickListener(v -> listener.verifyPayoutBeneficiary(v, listFilter.get(position)));
        holder.binding.checkStatusAccount.setOnClickListener(v -> listener.checkStatusPayoutBeneficiary(v, listFilter.get(position)));

        try {
            PayoutList model = listFilter.get(position);
            if(model.getVerified().equals("1") && model.getStatus().equals("1")){
                holder.binding.buttonsContainer.setVisibility(View.GONE);
            }else{
                holder.binding.buttonsContainer.setVisibility(View.VISIBLE);
                if (model.getVerified().equals("1")){
                    holder.binding.verifyAccount.setVisibility(View.GONE);
                    //check status, paste below else outside else if this didn't work
                    if (model.getStatus().equals("1")){
                        holder.binding.checkStatusAccount.setVisibility(View.GONE);
                    }
                    else{
                        holder.binding.checkStatusAccount.setVisibility(View.VISIBLE);
                    }
                }else{
                    holder.binding.verifyAccount.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


//        if(listFilter.get(position).getStatus()!=null && listFilter.get(position).getStatus().equals("2")){
//            holder.binding.verifyAccount.setVisibility(View.VISIBLE);
//        }else{
//            holder.binding.verifyAccount.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        if(listFilter==null){
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
                    List<PayoutList> filterList = new ArrayList<>();
                    for(PayoutList p : list){
                        if(p.getName().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getBankname().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getAccount().toLowerCase().contains(character.toLowerCase())){
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
                listFilter = (List<PayoutList>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class PayoutAccHolder extends RecyclerView.ViewHolder{
        PayoutListLayoutBinding binding;
        public PayoutAccHolder(@NonNull PayoutListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}