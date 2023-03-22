package com.fintech.petoindia.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fintech.petoindia.data.model.CircleModel;
import com.fintech.petoindia.databinding.CircleListLayoutBinding;
import com.fintech.petoindia.listeners.CircleListener;

import java.util.List;

public class CircleAdapter  extends  RecyclerView.Adapter<CircleAdapter.CircleViewHolder>{

    List<CircleModel> list;
    CircleListener listener;

    public CircleAdapter(List<CircleModel> list, CircleListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CircleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CircleListLayoutBinding binding = CircleListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CircleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CircleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.setCircleModel(list.get(position));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.MyCircleListener(v, list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }


    public static class CircleViewHolder extends RecyclerView.ViewHolder{
        CircleListLayoutBinding binding;
        public CircleViewHolder(@NonNull CircleListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
