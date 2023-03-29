package com.fintech.kkpayments.activities.profile;

import static com.fintech.kkpayments.util.Constants.AADHAAR_IMAGE;
import static com.fintech.kkpayments.util.Constants.PAN_IMAGE;
import static com.fintech.kkpayments.util.Constants.PASSBOOK_IMAGE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.data.db.AppDatabase;
import com.fintech.kkpayments.databinding.ActivityBankDetailsBinding;
import com.fintech.kkpayments.util.PathsInformation;
import com.fintech.kkpayments.util.PermissionUtil;
import com.fintech.kkpayments.util.ViewUtils;
import com.fintech.kkpayments.viewmodel.ProfileViewModel;

import java.io.File;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BankDetails extends AppCompatActivity {


    ActivityBankDetailsBinding binding;

    ProfileViewModel viewModel;

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
            if(userProfile!=null){
                binding.setUserProfileModel(userProfile);
                viewModel.userProfile = userProfile;
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
    }

    private void setMyDrawables(){
        binding.selectAadhar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectAadhar.setCompoundDrawablePadding(20);
        binding.selectPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPan.setCompoundDrawablePadding(20);
        binding.selectPassbook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPassbook.setCompoundDrawablePadding(20);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AADHAAR_IMAGE && resultCode == Activity.RESULT_OK){
            Uri content_describer = data.getData();
            binding.selectAadhar.setText(PathsInformation.getNameFromURI(getApplicationContext(), content_describer));
            String dest  = PathsInformation.getPathFromURI(getApplicationContext(), content_describer);
            File destination = null;
            if (dest != null) {
                destination = new File(dest);
            }
            viewModel.setAadhaar(destination);
        }
        if (requestCode == PAN_IMAGE && resultCode == Activity.RESULT_OK){
            Uri content_describer = data.getData();
            binding.selectPan.setText(PathsInformation.getNameFromURI(getApplicationContext(), content_describer));
            String dest  = PathsInformation.getPathFromURI(getApplicationContext(), content_describer);
            File destination = null;
            if (dest != null) {
                destination = new File(dest);
            }
            viewModel.setPan(destination);
        }
        if (requestCode == PASSBOOK_IMAGE && resultCode == Activity.RESULT_OK){
            Uri content_describer = data.getData();
            binding.selectPassbook.setText(PathsInformation.getNameFromURI(getApplicationContext(), content_describer));
            String dest  = PathsInformation.getPathFromURI(getApplicationContext(), content_describer);
            File destination = null;
            if (dest != null) {
                destination = new File(dest);
            }
            viewModel.setPassBook(destination);
        }
    }


}