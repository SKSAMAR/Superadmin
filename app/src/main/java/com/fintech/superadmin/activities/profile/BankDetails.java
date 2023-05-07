package com.fintech.superadmin.activities.profile;

import static com.fintech.superadmin.util.Constants.AADHAAR_IMAGE;
import static com.fintech.superadmin.util.Constants.PAN_IMAGE;
import static com.fintech.superadmin.util.Constants.PASSBOOK_IMAGE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.UserProfile;
import com.fintech.superadmin.data.repositories.UserRepository;
import com.fintech.superadmin.databinding.ActivityBankDetailsBinding;
import com.fintech.superadmin.util.PathsInformation;
import com.fintech.superadmin.util.PermissionUtil;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.ProfileViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BankDetails extends AppCompatActivity {

    ActivityBankDetailsBinding binding;
    ProfileViewModel viewModel;
    @Inject
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Bank Details");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setBankDetailsViewModel(viewModel);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(BankDetails.this);
        appDatabase.getUserProfileDao().getUserProfile().observe(this, userProfile -> {
            if (viewModel.userProfile == null) {
                viewModel.userProfile = userProfile;
                binding.setUserProfileModel(viewModel.userProfile);
            }
        });
        setMyDrawables();
        setListeners();
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


    private void setListeners(){

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("image/*");
        binding.selectAadhar.setOnClickListener(view -> {

            PermissionUtil.givePermission(BankDetails.this, data -> {
                if(data == 1){
                    startActivityForResult(
                            Intent.createChooser(chooseFile, "Choose a file"),
                            AADHAAR_IMAGE
                    );
                }
                else{
                    ViewUtils.showToast(getApplicationContext(), "No Permission");
                }
            });

        });
        binding.selectPan.setOnClickListener(view -> {

            PermissionUtil.givePermission(BankDetails.this, data -> {
                if(data == 1){
                    startActivityForResult(
                            Intent.createChooser(chooseFile, "Choose a file"),
                            PAN_IMAGE
                    );
                }
                else{
                    ViewUtils.showToast(getApplicationContext(), "No Permission");
                }
            });

        });
        binding.selectPassbook.setOnClickListener(view -> {
            PermissionUtil.givePermission(BankDetails.this, data -> {
                if(data == 1){
                    startActivityForResult(
                            Intent.createChooser(chooseFile, "Choose a file"),
                            PASSBOOK_IMAGE
                    );
                }
                else{
                    ViewUtils.showToast(getApplicationContext(), "No Permission");
                }
            });

        });


        binding.selectAadhar.setOnClickListener(view -> {
            ImagePicker.with(BankDetails.this).crop().compress(256).maxResultSize(1080, 1080).createIntent(intentention -> {
                selectAadhaarFrontLauncher.launch(intentention);
                return null;
            });
        });

        binding.selectPan.setOnClickListener(view -> {
            ImagePicker.with(BankDetails.this).crop().compress(256).maxResultSize(1080, 1080).createIntent(intentention -> {
                selectPanLauncher.launch(intentention);
                return null;
            });
        });

        binding.selectPassbook.setOnClickListener(view -> {
            ImagePicker.with(BankDetails.this).crop().compress(256).maxResultSize(1080, 1080).createIntent(intentention -> {
                selectPassbookLauncher.launch(intentention);
                return null;
            });
        });
    }

    @SuppressLint("SetTextI18n")
    private void setMyDrawables(){
        binding.selectAadhar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectAadhar.setCompoundDrawablePadding(20);
        binding.selectPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPan.setCompoundDrawablePadding(20);
        binding.selectPassbook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPassbook.setCompoundDrawablePadding(20);

        UserProfile userProfile = AppDatabase.getAppDatabase(BankDetails.this).getUserProfileDao().getRegularUserProfile();
        try {
            binding.selectAadhar.setText(""+userProfile.AADHAAR);
            binding.selectPan.setText(""+userProfile.PAN);
            binding.selectPassbook.setText(""+userProfile.PASSBOOK);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> selectAadhaarFrontLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Uri uri = Objects.requireNonNull(result.getData()).getData();
            binding.selectAadhar.setText("Selected");
            String dest  = PathsInformation.getPathFromURI(getApplicationContext(), uri);
            File destination = null;
            if (dest != null) {
                destination = new File(dest);
                binding.selectAadhar.setText(destination.getName());
            }
            viewModel.setAadhaar(destination);
        }
    });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> selectPanLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Uri uri = Objects.requireNonNull(result.getData()).getData();
            binding.selectPan.setText("Selected");
            String dest  = PathsInformation.getPathFromURI(getApplicationContext(), uri);
            File destination = null;
            if (dest != null) {
                destination = new File(dest);
                binding.selectPan.setText(destination.getName());
            }
            viewModel.setPan(destination);
        }
    });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> selectPassbookLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Uri uri = Objects.requireNonNull(result.getData()).getData();
            binding.selectPassbook.setText("Selected");
            String dest  = PathsInformation.getPathFromURI(getApplicationContext(), uri);
            File destination = null;
            if (dest != null) {
                destination = new File(dest);
                binding.selectPassbook.setText(destination.getName());
            }
            viewModel.setPassBook(destination);
        }
    });


    @Override
    protected void onResume() {
        super.onResume();
        userRepository.refreshUserData(BankDetails.this);
    }
}