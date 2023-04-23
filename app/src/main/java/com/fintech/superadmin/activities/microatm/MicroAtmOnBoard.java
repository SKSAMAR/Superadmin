package com.fintech.superadmin.activities.microatm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import com.fintech.superadmin.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.fintech.superadmin.R;
import com.fintech.superadmin.databinding.ActivityMicroAtmOnBoardBinding;
import com.fintech.superadmin.encoder.Base64Encoder;
import com.fintech.superadmin.viewmodel.OnBoardingViewModel;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MicroAtmOnBoard extends BaseActivity {


    OnBoardingViewModel viewModel;
    ActivityMicroAtmOnBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMicroAtmOnBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Onboard");
        viewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);
        binding.setOnBoardingViewModel(viewModel);
        viewModel.binding = binding;
        setListeners();
        setMyDrawables();

        binding.onBoardStatus.setText(getIntent().getStringExtra("message"));

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

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> frontAadharActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        binding.selectAadharFront.setText("Front Aadhaar Card Selected");
                        binding.selectAadharFront.setTextColor(Color.parseColor("#008000"));
                        viewModel.ADDHAR_FRONT_ENCODE = Base64Encoder.encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> backAadharActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        binding.selectAadharBack.setText("Back Aadhaar Card Selected");
                        binding.selectAadharBack.setTextColor(Color.parseColor("#008000"));
                        viewModel.ADDHAR_BACK_ENCODE = Base64Encoder.encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> frontPanActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        binding.selectPanFront.setText("Front Pan Card Selected");
                        binding.selectPanFront.setTextColor(Color.parseColor("#008000"));
                        viewModel.PAN_FRONT_ENCODE = Base64Encoder.encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> backPanActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        binding.selectPanBack.setText("Back Pan Card Selected");
                        binding.selectPanBack.setTextColor(Color.parseColor("#008000"));
                        viewModel.PAN_BACK_ENCODE = Base64Encoder.encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> serialNumberActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        binding.atmSerialPhoto.setText("Image Selected");
                        binding.atmSerialPhoto.setTextColor(Color.parseColor("#008000"));
                        viewModel.SERIAL_NUMBER_PHOTO_ENCODE = Base64Encoder.encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


    private void setListeners(){

        binding.selectAadharFront.setOnClickListener(v -> ImagePicker.with(MicroAtmOnBoard.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    frontAadharActivityResult.launch(intent);
                    return null;
                }));

        binding.selectAadharBack.setOnClickListener(v -> ImagePicker.with(MicroAtmOnBoard.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    backAadharActivityResult.launch(intent);
                    return null;
                }));

        binding.selectPanFront.setOnClickListener(v -> ImagePicker.with(MicroAtmOnBoard.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    frontPanActivityResult.launch(intent);
                    return null;
                }));

        binding.selectPanBack.setOnClickListener(v -> ImagePicker.with(MicroAtmOnBoard.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    backPanActivityResult.launch(intent);
                    return null;
                }));

        binding.atmSerialPhoto.setOnClickListener(v -> ImagePicker.with(MicroAtmOnBoard.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    serialNumberActivityResult.launch(intent);
                    return null;
                }));
    }

    private void setMyDrawables(){
        binding.selectAadharFront.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectAadharFront.setCompoundDrawablePadding(20);


        binding.selectAadharBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectAadharBack.setCompoundDrawablePadding(20);

        binding.selectPanFront.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPanFront.setCompoundDrawablePadding(20);

        binding.selectPanBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectPanBack.setCompoundDrawablePadding(20);

        binding.atmSerialPhoto.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.atmSerialPhoto.setCompoundDrawablePadding(20);

    }

}