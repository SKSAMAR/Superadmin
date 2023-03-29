package com.fintech.kkpayments.deer_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.data_model.plan.CFPlanModel;
import com.fintech.kkpayments.databinding.SubscriptionLayoutDesignBinding;
import com.fintech.kkpayments.deer_listener.ClickListener;

import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.MyViewHolder> {

    LayoutInflater inflater;
    ClickListener<CFPlanModel> listener;
    List<CFPlanModel> lists;

    public PagerAdapter(List<CFPlanModel> lists, ClickListener<CFPlanModel> listener){
        this.lists = lists;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        SubscriptionLayoutDesignBinding binding = DataBindingUtil.inflate(inflater, R.layout.subscription_layout_design, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setPlans(lists.get(position));
        holder.binding.selectPlans.setOnClickListener(v->listener.clickOn(lists.get(position)));
    }

    @Override
    public int getItemCount() {
        return (lists==null)?0: lists.size();
    }

    public void setListData(List<CFPlanModel> list){
        this.lists = list;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        SubscriptionLayoutDesignBinding binding;
        public MyViewHolder(@NonNull SubscriptionLayoutDesignBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
