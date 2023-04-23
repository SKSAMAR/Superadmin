package com.fintech.superadmin.activities.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.fintech.superadmin.activities.common.BaseActivity
import com.fintech.superadmin.adapters.CommissionAdapter
import com.fintech.superadmin.data.model.ComPackageModel
import com.fintech.superadmin.databinding.ActivityMyComissionBinding
import com.fintech.superadmin.viewmodel.ComViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MyCommissionActivity : BaseActivity() {

    val viewModel : ComViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar.title = "My Commission"
        actionBar.setDisplayHomeAsUpEnabled(true)
        viewModel.binding = ActivityMyComissionBinding.inflate(layoutInflater)
        setContentView(viewModel.binding.root)
        val list = ArrayList<ComPackageModel>()
        viewModel.adapter = CommissionAdapter(list)
        viewModel.binding.MyCommReportList.layoutManager = GridLayoutManager(this@MyCommissionActivity, 1, GridLayoutManager.VERTICAL, false)
        viewModel.binding.MyCommReportList.adapter = viewModel.adapter
        operations()
    }

    private fun operations(){
        viewModel.binding.optionTab.addOnTabSelectedListener(object :
            OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (Objects.requireNonNull(tab.text).toString()) {
                    "DMT" -> {
                        viewModel.getComPlans(type = "DMT_COMM", this@MyCommissionActivity)
                    }
                    "AePS" -> {
                        viewModel.getComPlans(type = "AEPS_COMM", this@MyCommissionActivity)
                    }
                    "MICRO ATM" -> {
                        viewModel.getComPlans(type = "M_ATM_COMM", this@MyCommissionActivity)
                    }
                    "RECHARGE" -> {
                        viewModel.getComPlans(type = "RC_COMM", this@MyCommissionActivity)
                    }
                    "BBPS" -> {
                        viewModel.getComPlans(type = "BBPS_COMM", this@MyCommissionActivity)
                    }
                    "FASTAG" -> {
                        viewModel.getComPlans(type = "FASTAG_COMM", this@MyCommissionActivity)

                    }
                    "AADHAAR" -> {
                        viewModel.getComPlans(type = "AADHAR_COMM", this@MyCommissionActivity)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewModel.getComPlans(context = this@MyCommissionActivity)
    }
}