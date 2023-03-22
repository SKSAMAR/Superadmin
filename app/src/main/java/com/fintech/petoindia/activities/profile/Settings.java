package com.fintech.petoindia.activities.profile;

import android.os.Bundle;
import android.view.MenuItem;

import com.fintech.petoindia.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.petoindia.data.db.AppDatabase;
import com.fintech.petoindia.databinding.ActivitySettingsBinding;
import com.fintech.petoindia.viewmodel.ProfileViewModel;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Settings extends BaseActivity {

    ActivitySettingsBinding binding;
    ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Change Password");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setProfileViewModel(viewModel);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(Settings.this);
        appDatabase.getUserDao().getUser().observe(this, user -> {
            if(user!=null){
                binding.setUserModel(user);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}