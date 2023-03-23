package com.fintech.webspidysoftware.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.webspidysoftware.data.network.responses.DmtUserData;
import com.fintech.webspidysoftware.databinding.DmtUserLayoutBinding;
import com.fintech.webspidysoftware.listeners.DMTAccountListener;

import java.util.ArrayList;
import java.util.List;

public class DmtUserAdapter extends RecyclerView.Adapter<DmtUserAdapter.UserHolder> implements Filterable {

    List<DmtUserData> listFilter;
    List<DmtUserData> list;
    LayoutInflater inflater;
    DMTAccountListener listener;

    public DmtUserAdapter(List<DmtUserData> list, DMTAccountListener listener){
        this.listener = listener;
        this.list = list;
        this.listFilter = new ArrayList<>();
        this.listFilter =list;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        DmtUserLayoutBinding binding = DmtUserLayoutBinding.inflate(inflater);
        return new UserHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.binding.setDmtUserData(listFilter.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.onAccountClick(v, listFilter.get(position)));

        holder.binding.refreshDmtUser.setOnClickListener(v -> listener.onRefreshClick(v, listFilter.get(position)));

        holder.binding.deleteDmtUser.setOnClickListener(v -> listener.onDeleteClick(v, listFilter.get(position)));


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
                    List<DmtUserData> filterList = new ArrayList<>();
                    for(DmtUserData p : list){
                        if(p.getMobile().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getFname().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getLname().toLowerCase().contains(character.toLowerCase())){
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
                listFilter = (List<DmtUserData>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class UserHolder extends RecyclerView.ViewHolder{
        DmtUserLayoutBinding binding;
        public UserHolder(@NonNull DmtUserLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
