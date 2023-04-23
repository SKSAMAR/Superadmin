package com.fintech.superadmin.activities.addfunds;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.fintech.superadmin.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.util.PathsInformation;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.fintech.superadmin.R;
import com.fintech.superadmin.databinding.ActivityRequestOfflineBinding;
import com.fintech.superadmin.listeners.ChangerListener;
import com.fintech.superadmin.viewmodel.FundViewModel;

import java.io.File;
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
                .createIntent(intentention -> {
                    someActivityResultLauncher.launch(intentention);
                    return null;
                }));
    }

    private void setMyDrawables(){
        binding.selectReceipt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_image_24, 0, 0, 0);
        binding.selectReceipt.setCompoundDrawablePadding(20);

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    String dest = PathsInformation.getPathFromURI(getApplicationContext(), uri);
                    File destination = null;
                    if (dest != null) {
                        destination = new File(dest);
                    }
                    binding.selectReceipt.setText("Receipt Selected Selected");
                    try {
                        binding.selectReceipt.setText(""+destination.getName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    viewModel.setProfileImage(destination, RequestOffline.this);
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