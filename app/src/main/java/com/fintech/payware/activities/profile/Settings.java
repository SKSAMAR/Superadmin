package com.fintech.payware.activities.profile;

import android.os.Bundle;
import android.view.MenuItem;

import com.fintech.payware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.payware.data.db.AppDatabase;
import com.fintech.payware.databinding.ActivitySettingsBinding;
import com.fintech.payware.viewmodel.ProfileViewModel;
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