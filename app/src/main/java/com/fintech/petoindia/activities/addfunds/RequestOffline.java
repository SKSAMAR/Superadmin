package com.fintech.petoindia.activities.addfunds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.fintech.petoindia.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.fintech.petoindia.R;
import com.fintech.petoindia.databinding.ActivityRequestOfflineBinding;
import com.fintech.petoindia.encoder.Base64Encoder;
import com.fintech.petoindia.listeners.ChangerListener;
import com.fintech.petoindia.viewmodel.FundViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RequestOffline extends BaseActivity implements ChangerListener {

    FundViewModel viewModel;
    ActivityRequestOfflineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestOfflineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Request Offline");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(FundViewModel.class);
        binding.setFundViewModel(viewModel);
        viewModel.listener = this;
        setMyDrawables();
        setListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            return true;
        }
        else if(item.getItemId() == R.id.history_check){
            Intent intent = new Intent(RequestOffline.this, RequestHistory.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setListeners(){
        binding.selectReceipt.setOnClickListener(view -> ImagePicker.with(RequestOffline.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    receiptResult.launch(intent);
                    return null;
                }));
    }

    private void setMyDrawables(){
        binding.selectReceipt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectReceipt.setCompoundDrawablePadding(20);

    }


    @SuppressLint("SetTextI18n")
    ActivityResultLauncher<Intent> receiptResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    final InputStream imageStream;
                    String encodedImage;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        encodedImage = Base64Encoder.encodeImage(selectedImage);
                        viewModel.receiptImageEncoded = encodedImage;
                        binding.selectReceipt.setText("Receipt Selected");
                        binding.selectReceipt.setTextColor(Color.parseColor("#008000"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

    @Override
    public void changeData(String value) {
        binding.transactionType.setText(value);
    }

    @Override
    public void eraseAll() {
        binding.selectReceipt.setText("");
        binding.transactionType.setText("");
        binding.amount.setText("");
        binding.remarks.setText("");
        binding.transactionID.setText("");
    }
}