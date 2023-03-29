package com.fintech.prepe.activities.creation;

import android.os.Bundle;

import com.fintech.prepe.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.prepe.databinding.ActivityCreationBinding;
import com.fintech.prepe.viewmodel.CreationViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreationActivity extends BaseActivity {


    ActivityCreationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CreationViewModel viewModel = new ViewModelProvider(this).get(CreationViewModel.class);
        binding.setCreationViewModel(viewModel);
    }
}