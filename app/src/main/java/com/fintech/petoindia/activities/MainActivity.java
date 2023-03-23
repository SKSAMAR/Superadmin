package com.fintech.petoindia.activities;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.petoindia.activities.common.BaseActivity;
import com.fintech.petoindia.databinding.ActivityMainBinding;
import com.fintech.petoindia.fragments.SignInFragment;
import com.fintech.petoindia.viewmodel.AuthViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

/**
@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    ActivityMainBinding activityMainBinding;
    AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setContentView(activityMainBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        if (savedInstanceState == null) {
            setFragment(new SignInFragment());
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(activityMainBinding.ScreenFrame.getId(), fragment);
        fragmentTransaction.commit();
    }

}
**/