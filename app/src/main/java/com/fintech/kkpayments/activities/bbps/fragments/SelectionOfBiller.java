package com.fintech.kkpayments.activities.bbps.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.databinding.FragmentSelectionOfBillerBinding;
import com.fintech.kkpayments.viewmodel.MobileRechargeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SelectionOfBiller extends Fragment {
    MobileRechargeViewModel viewModel;
    FragmentSelectionOfBillerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selection_of_biller, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MobileRechargeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(requireActivity());
        if(viewModel.menuModel.getTitle().contains("BBPS")){
            viewModel.op_category = "Electricity";
            binding.categoryBbps.setText("Electricity");
        }
        else {
            viewModel.op_category = viewModel.menuModel.getPassed_name();
            binding.categoryBbps.setText(viewModel.menuModel.getPassed_name());
        }
    }
}