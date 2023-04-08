package com.fintech.paytoindia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.paytoindia.R;
import com.fintech.paytoindia.data.dthinfo.DthInfoResponse;

import java.util.ArrayList;
import java.util.List;

public class DthInfoAdapter extends RecyclerView.Adapter<DthInfoAdapter.DthHolder> {

    List<? extends DthInfoResponse.Info> list;

    public DthInfoAdapter(List<? extends DthInfoResponse.Info> list){
        this.list = new ArrayList<>();
        this.list = list;
    }

    @NonNull
    @Override
    public DthHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem= inflater.inflate(R.layout.dth_info_design, parent, false);
        return new DthHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DthHolder holder, int position) {
        holder.customerName.setText(list.get(position).getCustomerName());
        holder.Balance.setText(list.get(position).getBalance());
        holder.monthlyRecharge.setText(list.get(position).getMonthlyRecharge());
        holder.nextRechargeDate.setText(list.get(position).getNextRechargeDate());
        holder.lastrechargeamount.setText(list.get(position).getLastrechargedate());
        holder.status.setText(list.get(position).getStatus());
        holder.planname.setText(list.get(position).getPlanname());
        holder.planname.setSelected(true);
        holder.lastrechargedate.setText(list.get(position).getLastrechargedate());

    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    static class DthHolder extends RecyclerView.ViewHolder{
        public TextView customerName,planname,lastrechargeamount,monthlyRecharge,lastrechargedate,nextRechargeDate,Balance,status;
        public DthHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            planname = itemView.findViewById(R.id.planname);
            lastrechargeamount = itemView.findViewById(R.id.lastrechargeamount);
            monthlyRecharge = itemView.findViewById(R.id.monthlyRecharge);
            lastrechargedate = itemView.findViewById(R.id.lastrechargedate);
            nextRechargeDate = itemView.findViewById(R.id.nextRechargeDate);
            Balance = itemView.findViewById(R.id.Balance);
            status = itemView.findViewById(R.id.status);
        }
    }
}
