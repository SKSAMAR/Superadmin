package com.fintech.kkpayments.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fintech.kkpayments.data.network.responses.SlabInfoResponse;
import com.fintech.kkpayments.databinding.SlabDataLayoutBinding;

import java.util.ArrayList;

public class SlabAdapter extends RecyclerView.Adapter<SlabAdapter.SlabViewHolder> {
    ArrayList<SlabInfoResponse> list;
    LayoutInflater inflater;

    public SlabAdapter(ArrayList<SlabInfoResponse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SlabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        SlabDataLayoutBinding binding = SlabDataLayoutBinding.inflate(inflater);
        return new SlabViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SlabViewHolder holder, int position) {
        holder.binding.setSlabInfoResponse(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public static class SlabViewHolder extends RecyclerView.ViewHolder{
        SlabDataLayoutBinding binding;
        public SlabViewHolder(@NonNull SlabDataLayoutBinding binding) {
            super(binding.getRoot());
             this.binding = binding;
        }
    }
}
