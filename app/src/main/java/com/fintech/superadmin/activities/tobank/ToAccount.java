package com.fintech.superadmin.activities.tobank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.superadmin.clean.presentation.dmt.transaction.DmtTransactionActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.model.BankModel;
import com.fintech.superadmin.data.network.responses.AddBeneficiaryResponse;
import com.fintech.superadmin.data.network.responses.BeneficiaryBank;
import com.fintech.superadmin.data.network.responses.BeneficiaryHistoryResponse;
import com.fintech.superadmin.databinding.ActivityToAccountBinding;
import com.fintech.superadmin.databinding.BottomBeneficiaryMoreSheetBinding;
import com.fintech.superadmin.databinding.JustVerifiedModalBinding;
import com.fintech.superadmin.listeners.BeneficiaryClickListener;
import com.fintech.superadmin.listeners.BeneficiaryHistoryListener;
import com.fintech.superadmin.listeners.ToBankListener;
import com.fintech.superadmin.masterListener.NotFoundListener;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.ToBankViewModel;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ToAccount extends BaseActivity implements ToBankListener, BeneficiaryClickListener, BeneficiaryHistoryListener, NotFoundListener {

    ProgressDialog dialog;
    ActivityToAccountBinding classBinding;
    ToBankViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classBinding = ActivityToAccountBinding.inflate(getLayoutInflater());
        setContentView(classBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("To Accounts");
        viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
        classBinding.setMyBankViewModel(viewModel);
        viewModel.listener = this;
        viewModel.notFoundListener = this;
        //searchPlate cast to view removed by the developer today..
        int search_plateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = classBinding.bankSearchView.findViewById(search_plateId);
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);
        View mSearchPlate2 = classBinding.historySearchView.findViewById(search_plateId);
        mSearchPlate2.setBackgroundColor(Color.TRANSPARENT);
        addTabs();
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        boolean check = getIntent().getBooleanExtra("verificationProcess", false);
        classBinding.myBankListRecycler.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        viewModel.getBeneficiaries(ToAccount.this, ToAccount.this, "", classBinding.myBankListRecycler, this, viewModel.globalSelectedMobile);
        provide(check);
        initialize();
    }


    private void provide(boolean result) {
        if (result) {
            viewModel.selectedBeneficiaryModel = (BeneficiaryBank) getIntent().getSerializableExtra("selectedBankModel");
            AddBeneficiaryResponse response = (AddBeneficiaryResponse) getIntent().getSerializableExtra("wholeInfoBeneficiary");
            Dialog dialog = new Dialog(ToAccount.this);
            JustVerifiedModalBinding binding = JustVerifiedModalBinding.inflate(LayoutInflater.from(ToAccount.this));
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            binding.verifyAccount.setOnClickListener(v -> {
                dialog.dismiss();

                String bene_acc = response.getData().getAccno();
                String bankCode = response.getData().getBankid();
                String beneName = response.getData().getName();
                String beneId = response.getData().getBene_id();
                viewModel.pennyDropSelf(ToAccount.this, beneId, bene_acc, bankCode, beneName, viewModel.globalSelectedMobile, res -> {
                    viewModel.getBeneficiaries(ToAccount.this, ToAccount.this, bene_acc, classBinding.myBankListRecycler, this, viewModel.globalSelectedMobile);
                });
            });
            binding.sendMoney.setOnClickListener(v -> {
                dialog.dismiss();

                viewModel.selectedBeneficiaryModel = viewModel.topBeneficiaryBank;
                Intent intent = new Intent(ToAccount.this, DmtTransactionActivity.class);
                intent.putExtra("number", viewModel.globalSelectedMobile);
                intent.putExtra("selectedBankModel", viewModel.selectedBeneficiaryModel);
                startActivity(intent);
            });
        }
    }


    private void addTabs() {
        classBinding.bankOptionTabs.addTab(classBinding.bankOptionTabs.newTab().setText("Bank\nAccounts"));
        classBinding.bankOptionTabs.addTab(classBinding.bankOptionTabs.newTab().setText("Histories"));
        setListeners();
    }

    private void setListeners() {
        classBinding.bankOptionTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (Objects.requireNonNull(tab.getText()).toString().equals("Histories")) {
                    classBinding.myBankListRecycler.setVisibility(View.GONE);
                    classBinding.myHistoryListRecycler.setVisibility(View.VISIBLE);
                    classBinding.historySearchView.setVisibility(View.VISIBLE);
                    classBinding.bankSearchView.setVisibility(View.GONE);

                    classBinding.historyLayout.setVisibility(View.VISIBLE);
                    classBinding.noHistoryRecords.setVisibility(View.GONE);
                    classBinding.bankLayout.setVisibility(View.GONE);
                    classBinding.loader.setVisibility(View.GONE);
                    classBinding.noBankRecords.setVisibility(View.GONE);


                    //Change some here...
                    viewModel.setAllHistories(ToAccount.this, "", classBinding.myHistoryListRecycler, ToAccount.this);

                } else {
                    classBinding.myBankListRecycler.setVisibility(View.VISIBLE);
                    classBinding.myHistoryListRecycler.setVisibility(View.GONE);
                    classBinding.historySearchView.setVisibility(View.GONE);
                    classBinding.bankSearchView.setVisibility(View.VISIBLE);

                    classBinding.historyLayout.setVisibility(View.GONE);
                    classBinding.noBankRecords.setVisibility(View.GONE);
                    classBinding.bankLayout.setVisibility(View.VISIBLE);
                    classBinding.loader.setVisibility(View.GONE);
                    classBinding.noHistoryRecords.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onStarted(String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setTitle(message);
        dialog.show();
    }

    @Override
    public void onCompleted(String message) {
        if (dialog != null) {
            dialog.dismiss();
            ViewUtils.showToast(ToAccount.this, message);
        }

    }

    @Override
    public void onError(String message) {
        ViewUtils.showToast(ToAccount.this, message);
    }

    @Override
    public void setAllBanks(ArrayList<BankModel> list) {

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClicked(View view, BeneficiaryBank model) {
        Intent intent = new Intent(ToAccount.this, DmtTransactionActivity.class);
        intent.putExtra("number", viewModel.globalSelectedMobile);
        intent.putExtra("selectedBankModel", model);
        startActivity(intent);
    }

    @Override
    public void onMoreClickListener(View view, BeneficiaryBank model) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.MyTransparentBottomSheetDialogTheme);
        BottomBeneficiaryMoreSheetBinding binding = BottomBeneficiaryMoreSheetBinding.inflate(LayoutInflater.from(view.getContext()));
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();
        bottomSheetDialog.setDismissWithAnimation(true);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        binding.cancelLayout.setOnClickListener(v -> bottomSheetDialog.dismiss());
        binding.removeLayout.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        viewModel.deleteThisBeneficiary(ToAccount.this, model.getBene_id(), model.getAccno(), classBinding.myBankListRecycler, ToAccount.this);
                        bottomSheetDialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(ToAccount.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });
        binding.historyLayout.setOnClickListener(v -> {
            Intent intent = new Intent(ToAccount.this, SelectedBeneficiaryHistory.class);
            intent.putExtra("selectedBankModel", model);
            startActivity(intent);
        });
        binding.pennyDrop.setOnClickListener(v -> {
            String bene_acc = model.getAccno();
            String bankCode = model.getBankid();
            String beneName = model.getName();
            String beneId = model.getBene_id();
            viewModel.pennyDropSelf(ToAccount.this, beneId, bene_acc, bankCode, beneName, viewModel.globalSelectedMobile, res -> {
                viewModel.getBeneficiaries(ToAccount.this, ToAccount.this, "", classBinding.myBankListRecycler, this, viewModel.globalSelectedMobile);
            });
        });
    }


    @Override
    public void clickOnMoreInfo(View view, BeneficiaryHistoryResponse history) {
        Intent intent = new Intent(ToAccount.this, DetailedHistoryScreen.class);
        intent.putExtra("selectedHistory", history);
        startActivity(intent);
    }

    @Override
    public void clickOnUpdateInfo(View view, BeneficiaryHistoryResponse history) {
        viewModel.updateDMTTransactionNow(ToAccount.this, history.getReference_id(), classBinding.myHistoryListRecycler, this, "all");
    }

    @Override
    public void notifierScreen(boolean result) {

    }

    @Override
    public void clickOnRefund(View view, BeneficiaryHistoryResponse history) {

        try {
            viewModel.applyForRefundDmtTransaction(ToAccount.this, ToAccount.this, history.getData().getAckno(), history.getReference_id());
        } catch (NullPointerException e) {
            DisplayMessageUtil.error(ToAccount.this, "Not Eligible for refund");
        }
    }

    @Override
    public void bringAllOverHistoryAgain(boolean status) {
        if (status) {
            viewModel.setAllHistories(ToAccount.this, classBinding.historySearchView.getQuery().toString(), classBinding.myHistoryListRecycler, ToAccount.this);
        }
    }

    private void initialize() {
        classBinding.bankSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (viewModel.adapter != null) {
                    viewModel.adapter.getFilter().filter(query);
                }
//                viewModel.getBeneficiaries( ToAccount.this,ToAccount.this,query, classBinding.myBankListRecycler, ToAccount.this, viewModel.globalSelectedMobile);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (viewModel.adapter != null) {
                    viewModel.adapter.getFilter().filter(newText);
                }

//                viewModel.getBeneficiaries(ToAccount.this,ToAccount.this,newText, classBinding.myBankListRecycler, ToAccount.this, viewModel.globalSelectedMobile);
                return false;
            }
        });

        classBinding.historySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setAllHistories(ToAccount.this, query, classBinding.myHistoryListRecycler, ToAccount.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setAllHistories(ToAccount.this, newText, classBinding.myHistoryListRecycler, ToAccount.this);
                return false;
            }
        });
    }

    @Override
    public void loading() {
        classBinding.loader.setVisibility(View.VISIBLE);
        classBinding.noBankRecords.setVisibility(View.GONE);
        classBinding.noHistoryRecords.setVisibility(View.GONE);
    }

    @Override
    public void notFound() {
        classBinding.noBankRecords.setVisibility(View.VISIBLE);
        classBinding.noHistoryRecords.setVisibility(View.VISIBLE);
        classBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void found() {
        classBinding.noBankRecords.setVisibility(View.GONE);
        classBinding.noHistoryRecords.setVisibility(View.GONE);
        classBinding.loader.setVisibility(View.GONE);
    }
}