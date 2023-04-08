package com.fintech.paytoindia.activities.bbps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.paytoindia.databinding.FragmentBillPaymentBinding;
import com.fintech.paytoindia.viewmodel.MobileRechargeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BillPayment extends Fragment {

    FragmentBillPaymentBinding binding;
    MobileRechargeViewModel mobileRechargeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBillPaymentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mobileRechargeViewModel = new ViewModelProvider(requireActivity()).get(MobileRechargeViewModel.class);
        binding.setViewModel(mobileRechargeViewModel);
        binding.edit.setOnClickListener(v-> binding.billNetAmount.setEnabled(true));
        binding.setLifecycleOwner(requireActivity());
    }
}