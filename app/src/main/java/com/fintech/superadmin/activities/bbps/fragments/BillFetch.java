package com.fintech.superadmin.activities.bbps.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintech.superadmin.R;
import com.fintech.superadmin.databinding.FragmentBillFetchBinding;
import com.fintech.superadmin.viewmodel.MobileRechargeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BillFetch extends Fragment {

    FragmentBillFetchBinding binding;
    MobileRechargeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_fetch, container, false);
        selectModes();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MobileRechargeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(requireActivity());
//        if (viewModel.selectedBBPS.getAd1_d_name() == null || viewModel.selectedBBPS.getAd1_d_name().trim().isEmpty()) {
//            binding.ad1Layout.setVisibility(View.GONE);
//        }
//
//        if (viewModel.selectedBBPS.getAd2_d_name() == null || viewModel.selectedBBPS.getAd2_d_name().trim().isEmpty()) {
//            binding.ad2Layout.setVisibility(View.GONE);
//        }
//
//        if (viewModel.selectedBBPS.getAd3_d_name() == null || viewModel.selectedBBPS.getAd3_d_name().trim().isEmpty()) {
//            binding.ad3Layout.setVisibility(View.GONE);
//        }
        selectModes();
    }

    @SuppressLint("SetTextI18n")
    private void selectModes() {
        try {
            if (viewModel.selectedBBPS.getRESPONSE().getBillerInfoResponse().getBiller().getBillerFetchRequiremet().equalsIgnoreCase("MANDATORY")) {

                binding.payMyBBPS.setVisibility(View.GONE);
                binding.directPayBBPS.setVisibility(View.GONE);
                binding.getDetails.setVisibility(View.VISIBLE);
                binding.fetchableConsumerLayout.setVisibility(View.VISIBLE);
            } else {
                binding.payMyBBPS.setVisibility(View.VISIBLE);
                binding.directPayBBPS.setVisibility(View.VISIBLE);
                binding.getDetails.setVisibility(View.GONE);
                binding.fetchableConsumerLayout.setVisibility(View.GONE);
                binding.mPinBBPS.setVisibility(View.VISIBLE);
                if (viewModel.selectedBBPS.getRESPONSE().getBillerInfoResponse().getBiller().getBillerCategory()  !=null){
                    binding.directPayCaNum.setHint(viewModel.selectedBBPS.getRESPONSE().getBillerInfoResponse().getBiller().getBillerCategory()+" Number");
                }else{
                    binding.directPayCaNum.setHint("Consumer Number");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}