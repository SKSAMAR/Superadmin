package com.fintech.superadmin.viewmodel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.eko_tobank.SelectBank;
import com.fintech.superadmin.activities.eko_tobank.SelectedBeneficiaryHistory;
import com.fintech.superadmin.adapters.BeneficiaryAdapter;
import com.fintech.superadmin.adapters.EkoBeneficiaryAdapter;
import com.fintech.superadmin.adapters.EkoSimpleBeneficiaryHistoryAdapter;
import com.fintech.superadmin.adapters.SimpleBeneficiaryHistoryAdapter;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.eko.BaseEkoResponse;
import com.fintech.superadmin.data.eko.EkoDmtFerchBeneficaryResponse;
import com.fintech.superadmin.data.eko.EkoDtmTransactionHistory;
import com.fintech.superadmin.data.eko.QueryRemitterResponse;
import com.fintech.superadmin.data.eko.RecipientListItem;
import com.fintech.superadmin.data.model.BankModel;
import com.fintech.superadmin.data.model.EkoBankModel;
import com.fintech.superadmin.data.network.dmt.FetchBeneficiaryResponse;
import com.fintech.superadmin.data.network.responses.BeneHistoryUpdateResponse;
import com.fintech.superadmin.data.network.responses.BeneficiaryBank;
import com.fintech.superadmin.data.network.responses.BeneficiaryDeleteResponse;
import com.fintech.superadmin.data.network.responses.BeneficiaryHistoryResponse;
import com.fintech.superadmin.data.network.responses.PennyDropResponse;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.data.repositories.EkoToBankRepository;
import com.fintech.superadmin.databinding.OtpScreenLayoutBinding;
import com.fintech.superadmin.deer_listener.Receiver;
import com.fintech.superadmin.listeners.BeneficiaryClickListener;
import com.fintech.superadmin.listeners.BeneficiaryHistoryListener;
import com.fintech.superadmin.listeners.DMTHomeListeners;
import com.fintech.superadmin.listeners.EkoBeneficiaryClickListener;
import com.fintech.superadmin.listeners.PayoutListener;
import com.fintech.superadmin.listeners.RemitterListener;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.listeners.SendAmountViewsListener;
import com.fintech.superadmin.listeners.ToBankListener;
import com.fintech.superadmin.listeners.ToEkoBankListener;
import com.fintech.superadmin.masterListener.NotFoundListener;
import com.fintech.superadmin.util.Accessable;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.NetworkUtil;
import com.fintech.superadmin.util.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class EkoToBankViewModel extends ViewModel {

    public EkoToBankRepository toBankRepository;
    public ToEkoBankListener listener;

    public static String remitter_dob = null;
    public static String pin_code = null;
    public static String remitter_mobile = null;
    public String remitter_first_name = null;
    public String remitter_last_name = null;
    public String globalSelectedMobile = null;
    public String remitter_address = null;
    public static QueryRemitterResponse MyQueryRemitterResponse;
    public RecipientListItem topBeneficiaryBank = null;
    //register_remitter
    public String remitter_otp = null;
    public String str = "";

    //register_remitter
    public RemitterListener remitterListener;
    public NotFoundListener notFoundListener;
    //add_beneficiary
    public String account_number = null;
    public String confirm_account_number = null;
    public String ifsc = null;
    public String account_holder_name = null;
    public String phone_number = null;
    public String nick_name = null;
    public EkoBankModel selectedBank;
    //add_beneficiary
    public String mPin = "";


    public EkoBeneficiaryAdapter adapter;
    //send amount
    public static String transType = null;
    public RecipientListItem selectedBeneficiaryModel = null;
    public static String amount = null;
    public PayoutListener payoutListener;
    public SendAmountViewsListener sendAmountViewsListener;

    //send amount
    public ResetListener resetListener;


    @Inject
    public EkoToBankViewModel(EkoToBankRepository toBankRepository) {
        this.toBankRepository = toBankRepository;
    }


    public void getBankListsOnline() {
        toBankRepository.getAllBanksFromApi(listener);
    }


    public void onAddBankPage(View view) {
        Intent intent = new Intent(view.getContext(), SelectBank.class);
        intent.putExtra("number", globalSelectedMobile);
        view.getContext().startActivity(intent);
    }


    public void onConfirmBeneficiary(View view) {
        if (globalSelectedMobile == null || globalSelectedMobile.isEmpty()) {
            MyAlertUtils.showServerAlertDialog(view.getContext(), "you are not authorised.");
        } else if (account_number == null || account_number.isEmpty()) {
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide Account number.");
        } else if (confirm_account_number == null || confirm_account_number.isEmpty()) {
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Confirm Account number.");
        } else if (!account_number.equals(confirm_account_number)) {
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Account Numbers don't match.");
        } else if (selectedBank.getSTATICIFSC() == null || selectedBank.getSTATICIFSC().trim().isEmpty()) {
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter valid IFSC code.");
        } else if (account_holder_name == null || account_holder_name.isEmpty()) {
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide valid Account holder name.");
        }
//        else if(remitter_dob == null || remitter_dob.isEmpty()){
//            MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide valid DOB.");
//        }
//        else if(remitter_address == null || remitter_address.isEmpty()){
//            MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide valid Pin Code.");
//        }
//        else if(pin_code == null || pin_code.isEmpty() || pin_code.length()<6){
//            MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide valid Pin Code.");
//        }
        else {
            toBankRepository.addMyBeneficiary(selectedBank.getID(), globalSelectedMobile, selectedBeneficiaryModel, view.getContext(), account_holder_name, selectedBank.getSTATICIFSC(), account_number, globalSelectedMobile);
        }
    }

    public void onQueryRemitterButtonClick(View view) {
        if (remitter_mobile == null || remitter_mobile.isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Provide valid Mobile Number");
        } else {
            toBankRepository.query_remitter_checking(remitter_mobile, view.getContext());
        }
    }


    public void onRegisterRemitterButtonClick(View view) {

        if (remitter_first_name == null || remitter_first_name.isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Provide valid First name");
        } else if (remitter_last_name == null || remitter_last_name.isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Provide valid Last name");
        } else if (remitter_mobile == null || remitter_mobile.isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Provide valid Last name");
        } else if (remitter_dob == null || remitter_dob.isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Provide valid Date of Birth");
        } else if (remitter_address == null || remitter_address.isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Provide valid Address");
        } else if (pin_code == null || pin_code.isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Provide valid Pin Code");
        } else {
            toBankRepository.register_remitter(view.getContext(), remitter_first_name, remitter_last_name, remitter_mobile, remitter_address, pin_code, remitter_dob);
        }

    }


    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateSelectionClick(View view) {
        new DatePickerDialog(view.getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        remitterListener.dateSetter(sdf.format(myCalendar.getTime()));
    }

    public void getBeneficiaries(NotFoundListener notFoundListener, Context context, String acc_num, RecyclerView recyclerView, EkoBeneficiaryClickListener listener, String givenMobile) {
        if (givenMobile != null) {
            notFoundListener.loading();
            toBankRepository.apiServices.ekobringBeneficiary(givenMobile, acc_num, "get_beneficiary")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EkoDmtFerchBeneficaryResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull EkoDmtFerchBeneficaryResponse beneficiaryBankList) {
                            MyAlertUtils.dismissAlertDialog();
                            try {
                                recyclerView.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false));
                                if (beneficiaryBankList.getStatus().equals(0) && (beneficiaryBankList.getResponseStatusId().equals(0)) && !beneficiaryBankList.getData().getRecipientList().isEmpty()) {
                                    MyAlertUtils.dismissAlertDialog();
                                    adapter = new EkoBeneficiaryAdapter(beneficiaryBankList.getData().getRecipientList(), listener);
                                    recyclerView.setAdapter(adapter);
                                    topBeneficiaryBank = beneficiaryBankList.getData().getRecipientList().get(0);
                                    notFoundListener.found();
                                } else {
                                    notFoundListener.notFound();
                                    EkoBeneficiaryAdapter adapter = new EkoBeneficiaryAdapter(null, listener);
                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (Exception e) {
                                notFoundListener.notFound();
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            notFoundListener.notFound();
                            MyAlertUtils.showServerAlertDialog(context, "Failed Due to: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }


    public void deleteThisBeneficiary(Context context, String bene_id, String bene_acc, RecyclerView recyclerView, EkoBeneficiaryClickListener listener) {
        if (globalSelectedMobile != null) {
            MyAlertUtils.showProgressAlertDialog(context);
            toBankRepository.apiServices.ekodeleteBeneficiary(bene_id, bene_acc, "delete_this", globalSelectedMobile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEkoResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull BaseEkoResponse deleteResponse) {
                            try {
                                if (deleteResponse.getResponseStatusId().equals(0) && globalSelectedMobile != null && deleteResponse.getStatus().equals(0)) {
                                    getBeneficiaries(notFoundListener, context, "", recyclerView, listener, globalSelectedMobile);
                                } else {
                                    DisplayMessageUtil.error(context, deleteResponse.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                DisplayMessageUtil.error(context, "" + deleteResponse.getMessage());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            DisplayMessageUtil.error(context, "Failed due to: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

    }

    public void onSpinnerClick(View view) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        List<String> list = new ArrayList<>();
        list.add("IMPS");
        list.add("NEFT");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
        myOperatorListView.setAdapter(adapter);
        searchOperator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
            payoutListener.onTransactionTypeChange(adapter.getItem(position));
            transType = adapter.getItem(position);
            dialog.dismiss();
        });
    }

    public void onSendDMTButtonClick(View view) {
        if (Accessable.isAccessable()) {
            if (amount == null || amount.trim().isEmpty()) {
                DisplayMessageUtil.error(view.getContext(), "Enter a valid amount");
                return;
            } else if (transType == null || transType.trim().isEmpty()) {
                DisplayMessageUtil.error(view.getContext(), "Select a valid Transaction type");
                return;
            } else if (mPin == null || mPin.trim().isEmpty()) {
                DisplayMessageUtil.error(view.getContext(), "Select a valid Transaction type");
                return;
            }
//            NetworkUtil.getNetworkResult(
//                    toBankRepository.apiServices.ekosendAmountDMT(mPin, selectedBeneficiaryModel.getBene_id(), amount, selectedBeneficiaryModel.getAccno(), transType, selectedBeneficiaryModel.getIfsc(), globalSelectedMobile),
//                    view.getContext(),
//                    result -> {
//                        sendAmountViewsListener.eraseAmountText();
//                        amount = null;
//                        transType = null;
//                        mPin = "";
//                        DisplayMessageUtil.dmtShow(view.getContext(), result.getDmtTransactions());
//                    }
//            );
        }
    }


    public void onHistoryCheckOfBeneficiary(View view) {
        Intent intent = new Intent(view.getContext(), SelectedBeneficiaryHistory.class);
        intent.putExtra("number", globalSelectedMobile);
        intent.putExtra("selectedBankModel", selectedBeneficiaryModel);
        view.getContext().startActivity(intent);
    }

    public void getSelectedBeneficiaryHistory(Context context, String reference, RecyclerView recyclerView, BeneficiaryHistoryListener<EkoDtmTransactionHistory> listener) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        User user = appDatabase.getUserDao().getRegularUser();
        if (user != null) {
            toBankRepository.apiServices.ekogetSelectedBeneficiaryHistory(reference, selectedBeneficiaryModel.getRecipientId().toString(), "selectedHistory")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<EkoDtmTransactionHistory>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            notFoundListener.loading();
                        }

                        @Override
                        public void onNext(@NonNull List<EkoDtmTransactionHistory> beneficiaryHistoryResponses) {
                            recyclerView.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(new EkoSimpleBeneficiaryHistoryAdapter(beneficiaryHistoryResponses, listener));
                            MyAlertUtils.dismissAlertDialog();
                            if (beneficiaryHistoryResponses.isEmpty()) {
                                listener.notifierScreen(true);
                                notFoundListener.notFound();
                            } else {
                                notFoundListener.found();
                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            notFoundListener.notFound();
                            MyAlertUtils.showServerAlertDialog(context, "Failed due to: " + e.getMessage());
                            listener.notifierScreen(true);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void updateDMTTransactionNow(Context context, String reference_id, RecyclerView recyclerView, BeneficiaryHistoryListener listener, String loc) {
        MyAlertUtils.showProgressAlertDialog(context);

        toBankRepository.apiServices.ekoupdateDMTTransaction(reference_id, "check_dmt_status")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeneHistoryUpdateResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BeneHistoryUpdateResponse beneHistoryUpdateResponse) {
                        if (beneHistoryUpdateResponse.isStatus() || beneHistoryUpdateResponse.response_code.equals(1)) {
                            DisplayMessageUtil.anotherDialogSuccess(context, beneHistoryUpdateResponse.getMessage());
                        } else {
                            DisplayMessageUtil.anotherDialogFailure(context, beneHistoryUpdateResponse.getMessage());
                        }
                        if (loc.equals("all")) {
                            setAllHistories(context, "", recyclerView, listener);
                        } else {
                            getSelectedBeneficiaryHistory(context, "", recyclerView, listener);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setAllHistories(Context context, String acc_num, RecyclerView recyclerView, BeneficiaryHistoryListener<EkoDtmTransactionHistory> listener) {
        notFoundListener.loading();
        toBankRepository.apiServices.ekogetAllBeneficiaryHistories(acc_num, globalSelectedMobile, "all_histories")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EkoDtmTransactionHistory>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<EkoDtmTransactionHistory> beneficiaryHistoryResponses) {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(new EkoSimpleBeneficiaryHistoryAdapter(beneficiaryHistoryResponses, listener));
                        MyAlertUtils.dismissAlertDialog();
                        if (beneficiaryHistoryResponses.isEmpty()) {
                            notFoundListener.notFound();
                        } else {
                            notFoundListener.found();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        notFoundListener.notFound();
                        DisplayMessageUtil.error(context, "Failed due to: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void deleteDMTUserAccount(String id, Context context, DMTHomeListeners listeners) {
        MyAlertUtils.showProgressAlertDialog(context);
        toBankRepository.apiServices.ekodeleteDmtUserAccount(id, "delete_dmt_user")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        if (response.isStatus() && response.getResponse_code().equals(1)) {
                            ViewUtils.showToast(context, response.getMessage());
                            toBankRepository.getAllMyDMTAccounts(listeners, "");
                        } else {
                            MyAlertUtils.showServerAlertDialog(context, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void applyForRefundDmtTransaction(BeneficiaryHistoryListener listener, Context context, String ackno, String refrence) {
        MyAlertUtils.showProgressAlertDialog(context);
        toBankRepository.apiServices.ekorefundDmtTransaction(ackno, refrence, "resendRefundOTP")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse regularResponse) {
                        if (regularResponse.isStatus() && regularResponse.getResponse_code().equals(1)) {
                            //Refund system
                            MyAlertUtils.dismissAlertDialog();
                            otpPassingDesign(listener, context, refrence, ackno);
                        } else {
                            MyAlertUtils.showServerAlertDialog(context, regularResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void otpPassingDesign(BeneficiaryHistoryListener listener, Context context, String refrence, String ackno) {
        Dialog dialog = new Dialog(context);
        OtpScreenLayoutBinding binding = OtpScreenLayoutBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        binding.cancelLayout.setOnClickListener(v -> dialog.dismiss());
        binding.verifyOtp.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.enterOtpDmt.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(context, "Provide OTP");
            } else {
                verifyRefundAmountOTP(listener, dialog, context, refrence, ackno, Objects.requireNonNull(binding.enterOtpDmt.getText()).toString());
            }
        });
    }

    private void verifyRefundAmountOTP(BeneficiaryHistoryListener listener, Dialog dialog, Context context, String refrence, String ackno, String otp) {
        MyAlertUtils.showProgressAlertDialog(context);
        toBankRepository.apiServices.ekoverifyDmtTransaction(ackno, refrence, otp, "refundDmt")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse regularResponse) {
                        if (regularResponse.isStatus() || regularResponse.getResponse_code().equals(1)) {
                            dialog.dismiss();
                            listener.bringAllOverHistoryAgain(true);
                            DisplayMessageUtil.anotherDialogSuccess(context, regularResponse.getMessage());
                        } else {
                            DisplayMessageUtil.error(context, regularResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void pennyDropSelf(Context context, String beneid, String acc, String bankid, String bankcode, String name, String mobile, Receiver<Boolean> receiver) {
        MyAlertUtils.showProgressAlertDialog(context);

        toBankRepository.apiServices.ekogetThePennyDrop(beneid, acc, bankid, name, mobile, "verify_name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEkoResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseEkoResponse response) {
                        try {
                            if (response.getStatus().equals(0) && response.getResponseTypeId().equals(0)) {
                                //String printMessage = response.getMessage() + "\nUTR: " + response.getUtr() + "\nName: " + response.getBenename();
                                DisplayMessageUtil.success(context, response.getMessage());
                                receiver.getData(true);
                            } else {
                                DisplayMessageUtil.error(context, response.getMessage());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
