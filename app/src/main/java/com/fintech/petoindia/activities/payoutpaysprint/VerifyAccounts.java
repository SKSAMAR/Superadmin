package com.fintech.petoindia.activities.payoutpaysprint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;

import com.fintech.petoindia.activities.common.BaseActivity;

import androidx.lifecycle.ViewModelProvider;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.fintech.petoindia.R;
import com.fintech.petoindia.databinding.ActivityVerifyAccountsBinding;
import com.fintech.petoindia.util.PathsInformation;
import com.fintech.petoindia.viewmodel.PayoutViewModel;

import java.io.File;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class VerifyAccounts extends BaseActivity {

    PayoutViewModel viewModel;
    ActivityVerifyAccountsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyAccountsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Verify Accounts");
        viewModel = new ViewModelProvider(this).get(PayoutViewModel.class);
        binding.setPayoutViewModel(viewModel);
        viewModel.bene_id = getIntent().getStringExtra("bene_id");
        viewModel.account_no = getIntent().getStringExtra("account_no");
        startConditionals();
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

    private void startConditionals() {
        binding.docSelection.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.pan) {
                binding.panSectionLayout.setVisibility(View.VISIBLE);
                binding.aadhaarSectionLayout.setVisibility(View.GONE);
                viewModel.doc_type = "PAN";
            } else {
                binding.panSectionLayout.setVisibility(View.GONE);
                binding.aadhaarSectionLayout.setVisibility(View.VISIBLE);
                viewModel.doc_type = "AADHAAR";
            }
        });
    }

    private void setMyDrawables() {
        binding.selectFrontAadhaar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectFrontAadhaar.setCompoundDrawablePadding(20);

        binding.selectBackAadhaar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectBackAadhaar.setCompoundDrawablePadding(20);

        binding.selectPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPan.setCompoundDrawablePadding(20);

        binding.selectPassbook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPassbook.setCompoundDrawablePadding(20);
    }


    private void setListeners() {
        binding.selectFrontAadhaar.setOnClickListener(view -> ImagePicker.with(VerifyAccounts.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent(intent -> {
                    frontAadhaarResultLauncher.launch(intent);
                    return null;
                }));

        binding.selectBackAadhaar.setOnClickListener(view -> ImagePicker.with(VerifyAccounts.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent(intent -> {
                    backAadhaarResultLauncher.launch(intent);
                    return null;
                }));


        binding.selectPan.setOnClickListener(view -> ImagePicker.with(VerifyAccounts.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent(intent -> {
                    panCardResultLauncher.launch(intent);
                    return null;
                }));
        binding.selectPassbook.setOnClickListener(view -> ImagePicker.with(VerifyAccounts.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent(intent -> {
                    passbookResultLauncher.launch(intent);
                    return null;
                }));
    }

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> panCardResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();

                    String path = PathsInformation.getPathFromURI(VerifyAccounts.this, uri);
                    File destination;
                    if (path != null) {
                        destination = new File(path);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), destination);
                        viewModel.pan = MultipartBody.Part.createFormData("pan", destination.getName(), requestFile);
                        binding.selectPan.setText("Pan Selected");
                        binding.selectPan.setTextColor(Color.parseColor("#008000"));
                    }
                }
            });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> passbookResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();

                    String path = PathsInformation.getPathFromURI(VerifyAccounts.this, uri);
                    File destination;
                    if (path != null) {
                        destination = new File(path);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), destination);
                        viewModel.passbook = MultipartBody.Part.createFormData("passbook", destination.getName(), requestFile);
                        binding.selectPassbook.setText("Passbook Selected");
                        binding.selectPassbook.setTextColor(Color.parseColor("#008000"));
                    }

                }
            });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> frontAadhaarResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    String path = PathsInformation.getPathFromURI(VerifyAccounts.this, uri);
                    File destination;
                    if (path != null) {
                        destination = new File(path);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), destination);
                        viewModel.afront = MultipartBody.Part.createFormData("afront", destination.getName(), requestFile);
                        binding.selectFrontAadhaar.setText("Front aadhaar Selected");
                        binding.selectFrontAadhaar.setTextColor(Color.parseColor("#008000"));
                    }
                }
            });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> backAadhaarResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    String path = PathsInformation.getPathFromURI(VerifyAccounts.this, uri);
                    File destination;
                    if (path != null) {
                        destination = new File(path);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), destination);
                        viewModel.aback = MultipartBody.Part.createFormData("aback", destination.getName(), requestFile);
                        binding.selectBackAadhaar.setText("Back aadhaar Selected");
                        binding.selectBackAadhaar.setTextColor(Color.parseColor("#008000"));
                    }
                }
            });
}