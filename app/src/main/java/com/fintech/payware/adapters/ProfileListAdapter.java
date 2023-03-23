package com.fintech.payware.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.payware.data.model.ProfileListModel;
import com.fintech.payware.databinding.ProfileEditLayoutBinding;
import com.fintech.payware.listeners.ProfileListListener;

import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ListViewHolder> {

    List<ProfileListModel> list;
    LayoutInflater inflater;
    ProfileListListener listener;
    public ProfileListAdapter(List<ProfileListModel> list, ProfileListListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        ProfileEditLayoutBinding binding = ProfileEditLayoutBinding.inflate(inflater, parent, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.binding.setModels(list.get(position));
        holder.binding.getRoot().setOnClickListener(view -> listener.onListItemSelected(view, list.get(position)));
    }

    @Override
    public int getItemCount() {
        if(list==null) {
            return 0;
        }
        return list.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        ProfileEditLayoutBinding binding;
        public ListViewHolder(@NonNull ProfileEditLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
