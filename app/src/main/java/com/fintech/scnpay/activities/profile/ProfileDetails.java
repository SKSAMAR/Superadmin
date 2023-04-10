package com.fintech.scnpay.activities.profile;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.scnpay.R;
import com.fintech.scnpay.data.db.AppDatabase;
import com.fintech.scnpay.databinding.ActivityProfileDetailsBinding;
import com.fintech.scnpay.viewmodel.ProfileViewModel;

import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileDetails extends AppCompatActivity {


    ProfileViewModel viewModel;

    ActivityProfileDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile Details");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setProfileDetailsModel(viewModel);
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
        AppDatabase appDatabase = AppDatabase.getAppDatabase(ProfileDetails.this);
        appDatabase.getUserProfileDao().getUserProfile().observe(this, userProfile -> {
            if(userProfile!=null){
                binding.setUserProfileModel(userProfile);
                viewModel.userProfile = userProfile;
                if(userProfile.GENDER!=null){
                   if(userProfile.GENDER.toLowerCase(Locale.ROOT).equals("male")){
                        binding.genderRadio.check(R.id.male);
                       userProfile.GENDER = "Male";
                    }
                    else if(userProfile.GENDER.toLowerCase(Locale.ROOT).equals("female")){
                        binding.genderRadio.check(R.id.fe_male);
                       userProfile.GENDER = "Female";
                    }
                    else{
                       binding.genderRadio.check(R.id.others);
                       userProfile.GENDER = "Others";
                   }
                }
            }
        });

        appDatabase.getUserDao().getUser().observe(this, user -> {
            if(user!=null){
                binding.setUserModel(user);
                viewModel.user = user;
            }
        });
    }
}