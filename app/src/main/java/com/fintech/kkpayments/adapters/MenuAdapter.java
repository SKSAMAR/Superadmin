package com.fintech.kkpayments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.data.model.MenuModel;
import com.fintech.kkpayments.databinding.RecyclerviewMenusOptionBinding;
import com.fintech.kkpayments.listeners.RecyclerViewClickListener;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    public List<MenuModel> menuList;
    public RecyclerViewClickListener listener;

    public MenuAdapter(List<MenuModel> menuList, RecyclerViewClickListener listener){
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewMenusOptionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.recyclerview_menus_option,parent, false);
        return new MenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.binding.setMyMenus(menuList.get(position));

            holder.binding.getRoot().setOnClickListener((View.OnClickListener) v -> listener.onRecyclerViewClickItem(v, menuList.get(position)));
    }

    @Override
    public int getItemCount() {
        if(menuList!=null){
            return menuList.size();
        }
        else{
            return 0;
        }
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder{
        RecyclerviewMenusOptionBinding binding;
        public MenuViewHolder(@NonNull RecyclerviewMenusOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
