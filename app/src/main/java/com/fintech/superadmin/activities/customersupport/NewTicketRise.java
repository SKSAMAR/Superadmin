package com.fintech.superadmin.activities.customersupport;

import static com.fintech.superadmin.util.Constant.*;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.databinding.ActivityNewTicketRiseBinding;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.util.PathsInformation;
import com.fintech.superadmin.util.PermissionUtil;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.CustomerSupportViewModel;

import java.io.File;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewTicketRise extends BaseActivity implements ResetListener {

    CustomerSupportViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CustomerSupportViewModel.class);
        viewModel.binding = ActivityNewTicketRiseBinding.inflate(getLayoutInflater());
        setContentView(viewModel.binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Customer Support");
        viewModel.resetListener = this;
        viewModel.binding.setViewModel(viewModel);
        setListeners();
    }


    private void setListeners(){

        try {

            String type = getIntent().getStringExtra("transactionType");
            String tid = getIntent().getStringExtra("transactionId");
            String date = getIntent().getStringExtra("transactionDate");
            viewModel.binding.departmentEdit.setText(type);
            viewModel.binding.transactionDate.setText(date);
            viewModel.binding.subject.setText(tid);
            viewModel.department = ""+type;
            viewModel.transactionId = ""+tid;
            viewModel.transactionDate = ""+date;

        }catch (Exception e){
            e.printStackTrace();
        }

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");

        viewModel.binding.selectProof.setOnClickListener(v-> PermissionUtil.givePermission(NewTicketRise.this, data -> {
            if(data == 1){
                startActivityForResult(
                        Intent.createChooser(chooseFile, "Choose a file"),
                        PROOF
                );
            }
            else{
                ViewUtils.showToast(getApplicationContext(), "No Permission");
            }
        }));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROOF && resultCode == Activity.RESULT_OK){
            Uri content_describer = data.getData();
            viewModel.binding.proofEdit.setText(PathsInformation.getNameFromURI(getApplicationContext(), content_describer));
            String dest  = PathsInformation.getPathFromURI(getApplicationContext(), content_describer);
            File destination = null;
            if (dest != null) {
                destination = new File(dest);
            }
            viewModel.setProof(destination);
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            return true;
        }
        else if(item.getItemId() == R.id.history_check){
            Intent intent = new Intent(NewTicketRise.this, TicketRiseHistory.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void resetRequiredData(boolean result) {
        if(result){
            viewModel.proof = null;
            viewModel.description = "";
            viewModel.transactionId = "";
            viewModel.transactionDate = "";
            viewModel.department = "";
            viewModel.binding.proofEdit.setText("");
            viewModel.binding.setViewModel(viewModel);
        }
    }
}