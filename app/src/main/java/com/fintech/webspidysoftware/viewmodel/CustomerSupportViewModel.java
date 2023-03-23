package com.fintech.webspidysoftware.viewmodel;

import android.app.DatePickerDialog;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.fintech.webspidysoftware.R;
import com.fintech.webspidysoftware.data.model.TicketHistoryModel;
import com.fintech.webspidysoftware.data.network.APIServices;
import com.fintech.webspidysoftware.databinding.ActivityNewTicketRiseBinding;
import com.fintech.webspidysoftware.deer_listener.Receiver;
import com.fintech.webspidysoftware.listeners.ResetListener;
import com.fintech.webspidysoftware.util.Accessable;
import com.fintech.webspidysoftware.util.DisplayMessageUtil;
import com.fintech.webspidysoftware.util.NetworkUtil;
import com.fintech.webspidysoftware.util.ViewUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@HiltViewModel
public class CustomerSupportViewModel extends ViewModel {

    APIServices apiServices;
    public String department = "";
    public String transactionId = "";
    public String description = "";
    public String transactionDate = "";

    public MultipartBody.Part proof;
    public ResetListener resetListener;
    public ActivityNewTicketRiseBinding binding;

    @Inject
    public CustomerSupportViewModel(APIServices apiServices){
        this.apiServices = apiServices;
    }

    public void selectDepartment(View view){
        TextInputEditText editText = view.getRootView().findViewById(R.id.department_edit);
        ViewUtils.onSpinnerViewBring("Select Department",view.getContext(), getListServices(), editText::setText);
    }


    public void raiseNewTicket(View view){
        if(Accessable.isAccessable()){
            if(department.trim().isEmpty()){
                DisplayMessageUtil.error(view.getContext(), "Select a valid Department");
            }
            else if(transactionId.trim().isEmpty()){
                DisplayMessageUtil.error(view.getContext(), "Enter a valid subject");
            }
            else if(transactionDate.trim().isEmpty()){
                DisplayMessageUtil.error(view.getContext(), "Select a valid Transaction Date");
            }
            else if(description.trim().isEmpty()){
                DisplayMessageUtil.error(view.getContext(), "Enter a valid description");
            }
//            else if(proof == null){
//                DisplayMessageUtil.error(view.getContext(), "Select a valid proof");
//            }
            else{
                RequestBody r_department = RequestBody.create(MediaType.parse("text/plain"), department);
                RequestBody r_trans_id = RequestBody.create(MediaType.parse("text/plain"), transactionId);
                RequestBody r_transaction_date = RequestBody.create(MediaType.parse("text/plain"), transactionDate);
                RequestBody r_description = RequestBody.create(MediaType.parse("text/plain"), description);


                NetworkUtil.getNetworkResult(apiServices.raiseAComplainTicket(proof, r_department, r_trans_id, r_transaction_date, r_description),
                        view.getContext(), data -> {
                            if(data.getResponse_code().equals(1)){
                                resetListener.resetRequiredData(true);
                                DisplayMessageUtil.success(view.getContext(), data.getMessage());
                            }
                            else{
                                DisplayMessageUtil.error(view.getContext(), data.getMessage());
                            }
                        });
            }
        }
    }

    public void getTicketHistory(String ticket_id, Receiver<List<TicketHistoryModel>> receiver){
        NetworkUtil.getNetworkResult(apiServices.getTicketHistory(ticket_id, "ticketHistory"), null, data -> receiver.getData(data.getReceivableData()));
    }


    public void setProof(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        proof = MultipartBody.Part.createFormData("proof", file.getName(), requestFile);
    }

    private List<String> getListServices(){
        List<String> list = new ArrayList<>();
        list.add("AePS");
        list.add("DMT");
        list.add("PAYOUT");
        list.add("RECHARGE");
        list.add("BBPS");
        list.add("INSURANCE");
        list.add("LOAN");
        list.add("PAN");
        list.add("OTHERS");
        return list;
    }


    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };


    public void onDateSelectionClick(View view){
        new DatePickerDialog(view.getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        binding.transactionDate.setText(sdf.format(myCalendar.getTime()));
    }
}
