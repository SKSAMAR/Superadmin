package com.fintech.paytcash.viewmodel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputEditText;
import com.fintech.paytcash.R;
import com.fintech.paytcash.activities.payoutpaysprint.PayoutAddAccount;
import com.fintech.paytcash.activities.payoutpaysprint.PaysprintPayout;
import com.fintech.paytcash.data.model.BankModel;
import com.fintech.paytcash.data.model.PayoutUserModel;
import com.fintech.paytcash.data.network.responses.PayoutList;
import com.fintech.paytcash.data.repositories.PayoutRepository;
import com.fintech.paytcash.databinding.SendPayoutDesignBinding;
import com.fintech.paytcash.listeners.PayoutListener;
import com.fintech.paytcash.util.Accessable;
import com.fintech.paytcash.util.DisplayMessageUtil;
import com.fintech.paytcash.util.MyAlertUtils;
import com.fintech.paytcash.util.NetworkUtil;
import com.fintech.paytcash.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@HiltViewModel
public class PayoutViewModel extends ViewModel {

    public String accountNumber = "";
    public String IFSC = "";
    public String enteredAmount = "";
    public String beneficiaryName = "";
    public String transType = "";
    public String enteredOtp = "";
    public PayoutListener listener;
    public PayoutRepository repository;
    private long mLastClickTime = 0;

    private ArrayList<BankModel> bankList;
    private String bank_id = "";
    private String bank_name = "";

    //verification
    public String account_no = "";
    public String doc_type = "";
    public String bene_id = "";


    public MultipartBody.Part passbook;
    public MultipartBody.Part afront;
    public MultipartBody.Part aback;
    public MultipartBody.Part pan;
    //verification
    String mode = "";
    String verify = "";

    MutableLiveData<PayoutState> _payoutState = new MutableLiveData<>(PayoutState.OTP_STATE);
    public LiveData<PayoutState> payoutState = _payoutState;


    @Inject
    public PayoutViewModel(PayoutRepository repository){
        this.repository = repository;
    }


    public void onPayToBankClick(View view){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        else {

            if (accountNumber == null || accountNumber.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Account Number");
            } else if (IFSC == null || IFSC.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide IFSC Number");
            } else if (enteredAmount == null || enteredAmount.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Amount");
            } else if (transType == null || transType.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Transaction type");
            } else {
                try{
                    long minBalance = Long.parseLong(enteredAmount);
                    if(minBalance<100){
                        DisplayMessageUtil.error(view.getContext(), "Minimum amount should be at-least 100 Rs.");
                        return;
                    }
                }catch (NumberFormatException exception){
                    DisplayMessageUtil.error(view.getContext(), "Minimum amount should be at-least 100 Rs.");
                    return;
                }

                repository.reAuthenticateTheUser(view.getContext(), enteredAmount, accountNumber, IFSC, transType);
            }
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }


    public void cfPayoutClick(View view){
        if(Accessable.isAccessable()){
         if(enteredAmount==null || enteredAmount.isEmpty()){
                DisplayMessageUtil.error(view.getContext(), "Enter a valid amount");
            }
            else if(enteredOtp==null || enteredOtp.isEmpty()){
                DisplayMessageUtil.error(view.getContext(), "Enter a valid Otp");
            }
            else{
                DisplayMessageUtil.loading(view.getContext());
                repository.apiServices.cfPayoutSendAmount(enteredAmount, enteredOtp, verify)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(r->{
                            if(r.getStatus() && r.getResponse_code().equals(1)){
                                DisplayMessageUtil.success(view.getContext(),r.getMessage());
                                _payoutState.setValue(PayoutState.OTP_STATE);
                            }
                            else{
                                DisplayMessageUtil.error(view.getContext(),r.getMessage());
                            }
                            },error->DisplayMessageUtil.error(view.getContext(), error.getLocalizedMessage()));
            }

        }
    }

    public LiveData<PayoutUserModel> getPayoutUserData(Context context){
        DisplayMessageUtil.loading(context);
        MutableLiveData<PayoutUserModel> model = new MutableLiveData<>();
        repository.apiServices.getPayoutUserValues("payout_account_disp")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( resp->{
                    DisplayMessageUtil.dismissDialog();
                    if(resp.getStatus() && resp.getResponse_code().equals(1)){
                        Log.e("payout_account_disp", resp.getReceivableData().toString());
                        model.setValue(resp.getReceivableData());
                    }
                    else{
                        DisplayMessageUtil.error(context, resp.getMessage());
                    }
                }, err-> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
        return model;
    }

    public void cfPayoutSendOtpClick(View view){
        if(Accessable.isAccessable()){
            if(enteredAmount==null || enteredAmount.isEmpty()){
                DisplayMessageUtil.error(view.getContext(), "Enter a valid amount");
            }
            else{
                DisplayMessageUtil.loading(view.getContext());
                repository.apiServices.cfPayoutSendOtp(enteredAmount, "sendotp")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(r->{
                            String res = r.getReceivableData().getSmsotpst().toLowerCase();
                            if(res.equals("success")){
                                DisplayMessageUtil.dismissDialog();
                                verify = r.getReceivableData().getOTPHASH();
                                _payoutState.setValue(PayoutState.TRANSACTION_STATE);
                            }
                            else{
                                DisplayMessageUtil.success(view.getContext(),r.getReceivableData().getSmsotpst());
                            }
                        },error->DisplayMessageUtil.error(view.getContext(), error.getLocalizedMessage()));
            }

        }
    }



    public void verifyPayoutDocuments(View view){
        if (Accessable.isAccessable()) {

            if (doc_type.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select Document Type");
            } else if (passbook == null) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select Passbook Image");
            } else if(doc_type.equals("PAN")){

                if(pan == null){
                    MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select Pan Card");
                }
                else{

                    NetworkUtil.getNetworkResult(
                            repository.apiServices.verifyPayoutAccount(pan, passbook, getTextPlain(bene_id), getTextPlain(doc_type)),
                            view.getContext(),
                            response->{
                                if(response.isStatus() && response.getResponse_code()==1){
                                    ViewUtils.showToast(view.getContext(), response.getMessage());
                                    Intent intent = new Intent(view.getContext(), PaysprintPayout.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    view.getContext().startActivity(intent);

                                }else{
                                    DisplayMessageUtil.error(view.getContext(), response.getMessage());
                                }
                            }
                    );

                }

            }else if(doc_type.equals("AADHAAR")){

                if(afront == null){
                    MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select Front Aadhaar");
                }
                else if(aback == null){
                    MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select Back Aadhaar");
                }
                else{

                    NetworkUtil.getNetworkResult(
                            repository.apiServices.verifyPayoutAccount(afront, aback, passbook, getTextPlain(bene_id), getTextPlain(doc_type)),
                            view.getContext(),
                            response->{
                                if(response.isStatus() && response.getResponse_code()==1){
                                    ViewUtils.showToast(view.getContext(), response.getMessage());
                                    Intent intent = new Intent(view.getContext(), PaysprintPayout.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    view.getContext().startActivity(intent);

                                }else{
                                    DisplayMessageUtil.error(view.getContext(), response.getMessage());
                                }
                            }
                    );
                }
            }
        }
    }


    public void onSpinnerClick(View view){
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,list);
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
            listener.onTransactionTypeChange(adapter.getItem(position));
            transType = adapter.getItem(position);
            dialog.dismiss();
        });
    }

    public void addMorePayoutAccount(View view){
        view.getContext().startActivity(new Intent(view.getContext(), PayoutAddAccount.class));
    }

    public void onAddPayoutAccountButton(View view){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
            return;
        }
        else {

            if (accountNumber == null || accountNumber.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Account Number");
            } else if (IFSC == null || IFSC.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide IFSC Number");
            } else if (beneficiaryName == null || beneficiaryName.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Beneficiary Name");
            }
            else if (bank_id == null || bank_id.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select a bank");
            }
            else {
                repository.addPayoutAccount(view.getContext(), bank_id, accountNumber, IFSC, beneficiaryName);
            }
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }


    public void onSelectBankClick(View view) {
        TextInputEditText editText = view.findViewById(R.id.bankName);
        if(bankList==null){
            getAllBanksFromApi(editText);
        }
        else{
            onSpinnerBankClick(editText);
        }
    }


    public void getAllBanksFromApi(TextInputEditText editText){
        MyAlertUtils.showProgressAlertDialog(editText.getContext());
        repository.apiServices.getAllBanks("getBanks")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<BankModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ArrayList<BankModel> bankModelList) {
                        MyAlertUtils.dismissAlertDialog();
                        bankList = bankModelList;
                        onSpinnerBankClick(editText);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(editText.getContext(), "Failed due to\n"+e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @SuppressLint("SetTextI18n")
    public void onSpinnerBankClick(TextInputEditText editText){
        Dialog dialog = new Dialog(editText.getContext());
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        TextView title = dialog.findViewById(R.id.head_title_section);
        title.setText("Select Bank");
        ArrayAdapter<BankModel> adapter = new ArrayAdapter<>(editText.getContext(), android.R.layout.simple_list_item_1,bankList);
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
            editText.setText(adapter.getItem(position).getBankname());
            bank_id = adapter.getItem(position).getBankid();
            bank_name = adapter.getItem(position).getBankname();
            dialog.dismiss();
        });
    }


    public void sendPayoutScreen(String bene_id, Context context, PayoutList data){
        mode = "";
        SendPayoutDesignBinding binding = SendPayoutDesignBinding.inflate(LayoutInflater.from(context));
        Dialog dialog = new Dialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        binding.cancel.setOnClickListener(v -> dialog.dismiss());

        binding.paySelection.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.imps){
                mode = "IMPS";
            }
            else{
                mode = "NEFT";
            }
        });

        binding.sendAmountUser.setOnClickListener(v -> {
            String amount = Objects.requireNonNull(binding.amount.getText()).toString();
            if(mode.isEmpty()){
                ViewUtils.showToast(context, "Select payment mode");
            }
            else if(amount.isEmpty()){
                binding.amount.setError("Enter amount");
                binding.amount.requestFocus();
            }
            else{
                try{
                    long checkBalance = Long.parseLong(amount);
                    if(checkBalance>0 && checkBalance<=200000){
                        repository.sendMoneyPayouts(context, dialog, bene_id, mode, amount, data.getAccount(), data.getIfsc());
                    }
                    else{
                        MyAlertUtils.showWarningAlertDialog(context, "Please make transaction between 100 Rs & 200000");
                    }
                }catch (NumberFormatException exception){
                    MyAlertUtils.showWarningAlertDialog(context, "Provide a valid amount");
                }
            }
        });
    }


    public

    enum PayoutState{
        OTP_STATE,
        TRANSACTION_STATE
    }

    private RequestBody getTextPlain(String value){
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}
