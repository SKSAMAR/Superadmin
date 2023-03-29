package com.fintech.kkpayments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.data.model.ImageModel;
import com.fintech.kkpayments.databinding.ImageCaptureDesignBinding;
import com.fintech.kkpayments.databinding.RecyclerviewMenusOptionBinding;
import com.fintech.kkpayments.listeners.RecyclerViewClickListener;

import java.util.List;

public class ImagesCamAdapter extends RecyclerView.Adapter<ImagesCamAdapter.CamViewHolder> {

    public List<ImageModel> camList;
    public RecyclerViewClickListener listener;

    public ImagesCamAdapter(List<ImageModel> camList, RecyclerViewClickListener listener){
        this.camList = camList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageCaptureDesignBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.recyclerview_menus_option,parent, false);
        return new CamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CamViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.binding.setImageModel(camList.get(position));
//            holder.binding.getRoot().setOnClickListener(v -> listener.onRecyclerViewClickItem(v, camList.get(position)));
    }

    @Override
    public int getItemCount() {
        if(camList!=null){
            return camList.size();
        }
        else{
            return 0;
        }
    }
    

    public static class CamViewHolder extends RecyclerView.ViewHolder{
        ImageCaptureDesignBinding binding;
        public CamViewHolder(@NonNull ImageCaptureDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
