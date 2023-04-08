package com.fintech.paytoindia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fintech.paytoindia.data.model.ComPackageModel
import com.fintech.paytoindia.databinding.MyCommReportModelBinding

class CommissionAdapter(var list: ArrayList<ComPackageModel>):
    RecyclerView.Adapter<CommissionAdapter.CommViewHolder>() {


    data class CommViewHolder(val myCommReportModelBinding: MyCommReportModelBinding):
        RecyclerView.ViewHolder(myCommReportModelBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommViewHolder {
        val myCommReportModelBinding: MyCommReportModelBinding = MyCommReportModelBinding.inflate(
            LayoutInflater.from(parent.context))
        return CommViewHolder(myCommReportModelBinding)
    }

    override fun onBindViewHolder(holder: CommViewHolder, position: Int) {
        holder.myCommReportModelBinding.commissionModelPack = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun resetAll(){
        this.list = ArrayList()
        notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<ComPackageModel>){
        this.list = ArrayList()
        this.list.addAll(list)
        notifyDataSetChanged()
    }



}