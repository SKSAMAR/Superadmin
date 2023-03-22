package com.fintech.petoindia.activities.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.fintech.petoindia.data.db.AppDatabase;
import com.fintech.petoindia.databinding.ActivitySocialMediaBinding;
import com.fintech.petoindia.viewmodel.ProfileViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SocialMedia extends AppCompatActivity {


    ActivitySocialMediaBinding binding;
    ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySocialMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Social Media");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setProfileViewModel(viewModel);
        setElements();
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
    private void setElements(){
        AppDatabase appDatabase = AppDatabase.getAppDatabase(SocialMedia.this);
        appDatabase.getUserProfileDao().getUserProfile().observe(this, userProfile -> {
            if(userProfile!=null){
                binding.setUserProfileModel(userProfile);
                viewModel.userProfile = userProfile;
            }
        });
    }
}