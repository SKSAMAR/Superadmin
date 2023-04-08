package com.fintech.paytoindia.activities.bbps.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintech.paytoindia.R;
import com.fintech.paytoindia.databinding.FragmentBillFetchBinding;
import com.fintech.paytoindia.viewmodel.MobileRechargeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BillFetch extends Fragment {

    FragmentBillFetchBinding binding;
    MobileRechargeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_fetch, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MobileRechargeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(requireActivity());

        if(viewModel.selectedBBPS.getAd1_d_name() == null || viewModel.selectedBBPS.getAd1_d_name().trim().isEmpty()){
            binding.ad1Layout.setVisibility(View.GONE);
        }

        if(viewModel.selectedBBPS.getAd2_d_name() == null || viewModel.selectedBBPS.getAd2_d_name().trim().isEmpty()){
            binding.ad2Layout.setVisibility(View.GONE);
        }

        if(viewModel.selectedBBPS.getAd3_d_name() == null || viewModel.selectedBBPS.getAd3_d_name().trim().isEmpty()){
            binding.ad3Layout.setVisibility(View.GONE);
        }


    }
}