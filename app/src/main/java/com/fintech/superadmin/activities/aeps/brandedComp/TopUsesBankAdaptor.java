package com.fintech.superadmin.activities.aeps.brandedComp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.R;
import com.fintech.superadmin.data.model.AEPSBanksModel;

import java.util.ArrayList;


public class TopUsesBankAdaptor extends RecyclerView.Adapter<TopUsesBankAdaptor.MyViewHolder> {
    private Context mContext;
    private ArrayList<AEPSBanksModel> bankListData;
    private RecyclerViewClickListener listener;

    public TopUsesBankAdaptor(Context context, ArrayList<AEPSBanksModel> bankList, RecyclerViewClickListener listener) {
        this.listener = listener;
        this.mContext = context;
        this.bankListData = bankList;
    }


    public int getItemCount() {
        return this.bankListData.size();
    }

    public class MyViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView iv_topBanks;
        private TextView textBankState;
        private LinearLayout linearState;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.iv_topBanks = (ImageView) itemView.findViewById(R.id.iv_topBanks);
            this.textBankState = (TextView) itemView.findViewById(R.id.textBankState);
            this.linearState = (LinearLayout) itemView.findViewById(R.id.linearState);
        }


        public void onClick(View v) {
            TopUsesBankAdaptor.this.listener.onClick(this.itemView, getAdapterPosition());
        }
    }


    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.w_top_banks_view, viewGroup, false);

        return new MyViewHolder(view);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.setIsRecyclable(false);
        AEPSBanksModel bankListModel = this.bankListData.get(i);
        holder.iv_topBanks.setImageResource(this.bankListData.get(i).temp_image);
        holder.textBankState.setText(bankListModel.getBankname());
    }
}