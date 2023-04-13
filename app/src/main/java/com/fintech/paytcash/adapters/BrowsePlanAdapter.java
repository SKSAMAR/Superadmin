//package com.paytcash.paydeer.adapters;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import androidx.recyclerview.widget.RecyclerView;
//import com.paytcash.paydeer.R;
//import com.paytcash.paydeer.activities.rechargeactivities.BrowsePlan;
//import com.paytcash.paydeer.activities.rechargeactivities.RechargeMyPlan;;
//import com.paytcash.paydeer.data.network.responses.BrowsePlanResponse;
//
//import java.util.List;
//
//
//public class BrowsePlanAdapter extends RecyclerView.Adapter<BrowsePlanAdapter.ViewHolder>{
//    List<BrowsePlanResponse> myPlans;
//    Context mContext;
//    // RecyclerView recyclerView;
//    public BrowsePlanAdapter(List<BrowsePlanResponse> myPlans) {
//        this.myPlans = myPlans;
//    }
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        return new ViewHolder();
//
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        if(myPlans==null){
//            return 0;
//        }
//        return myPlans.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//
//}