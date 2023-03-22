package com.fintech.petoindia.activities.creation;

import android.os.Bundle;

import com.fintech.petoindia.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.petoindia.databinding.ActivityCreationBinding;
import com.fintech.petoindia.viewmodel.CreationViewModel;

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