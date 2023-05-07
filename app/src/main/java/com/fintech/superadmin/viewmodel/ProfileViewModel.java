package com.fintech.superadmin.viewmodel;

import static com.fintech.superadmin.util.Constant.OTP_TIMES;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.android.material.textfield.TextInputEditText;
import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.profile.ProfileDetails;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.db.entities.UserProfile;
import com.fintech.superadmin.data.deer_response.CFPOTPResponse;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.data.network.responses.SystemResponse;
import com.fintech.superadmin.data.repositories.ProfileRepository;
import com.fintech.superadmin.data.sys.TPinResponse;
import com.fintech.superadmin.deer_listener.TaskListener;
import com.fintech.superadmin.util.Accessable;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.ViewUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@HiltViewModel
public class ProfileViewModel extends ViewModel {

    public User user;
    public UserProfile userProfile;
    public ProfileRepository profileRepository;
    @SuppressLint("StaticFieldLeak")
    TextInputEditText dob_editText;

    public String old_pass = "";
    public String new_pass = "";
    public String confirm_pass = "";
    public String m_pin = "";
    public String otp = "";

    public String old_m_pin = "";
    public String new_m_pin =  "";

    public MultipartBody.Part aadhaar;
    public MultipartBody.Part pan;
    public MultipartBody.Part passbook;

    CFPOTPResponse otpResponse;
    public TaskListener<RegularResponse> taskListener;

    public MutableLiveData<M_PIN_STATE> _pinState = new MutableLiveData<>(M_PIN_STATE.CREATION);
    public LiveData<M_PIN_STATE> pinState = _pinState;

    @Inject
    public ProfileViewModel(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }
    public void onEditDetails(View view){
        view.getContext().startActivity(new Intent(view.getContext(), ProfileDetails.class));
    }

    public void selectDOB(View view){
        dob_editText = view.findViewById(R.id.selectedDOB);
        new DatePickerDialog(view.getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void updateProfileData(View view){
        if(user.getName().trim().isEmpty() || user.getLastname().trim().isEmpty() || userProfile.getDOB()==null || userProfile.getDOB().trim().isEmpty() || userProfile.GENDER==null || userProfile.GENDER.trim().isEmpty() ||
                userProfile.COUNTRY==null || userProfile.COUNTRY.trim().isEmpty() || userProfile.STATE==null || userProfile.STATE.trim().isEmpty() || user.pin==null || user.pin.trim().isEmpty() ||
                user.address==null || user.address.trim().isEmpty()){
            ViewUtils.showToast(view.getContext(), "Provide all the information..");
        }
        else{
            profileRepository.updateProfileDetails(view.getContext(), userProfile, user);
        }
    }

    public void selectTheGender(RadioGroup radioGroup, int id){
        if(radioGroup.getCheckedRadioButtonId()== R.id.male){
            userProfile.GENDER = "Male";
        }
        else if(radioGroup.getCheckedRadioButtonId()== R.id.fe_male){
            userProfile.GENDER = "Female";
        }
        else if(radioGroup.getCheckedRadioButtonId()== R.id.others){
            userProfile.GENDER = "Others";
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

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        userProfile.setDOB(sdf.format(myCalendar.getTime()));
        dob_editText.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateSocialMediaData(View view){
        try {
            User user = AppDatabase.getAppDatabase(view.getContext()).getUserDao().getRegularUser();
            profileRepository.updateSocialMediaDetails(view.getContext(), userProfile, user);
        }
        catch (NullPointerException e){
            ViewUtils.showToast(view.getContext(), "Failed due to: "+e.getMessage());
        }
    }

    public void updateBankDetailsData(View view){
        if(Accessable.isAccessable()){
            if(userProfile != null){
                /**
                 if(userProfile.AADHAAR == null || userProfile.AADHAAR.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Select a valid Aadhaar");
                 }
                 else if(userProfile.AADHAR_CARD_NO == null || userProfile.AADHAR_CARD_NO.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Enter a valid Aadhaar number");
                 }
                 else if(userProfile.PAN == null || userProfile.PAN.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Select a valid Pan");
                 }
                 else if(userProfile.PAN_CARD_NO == null || userProfile.PAN_CARD_NO.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Enter a valid Pan number");
                 }
                 else if(userProfile.BANK == null || userProfile.BANK.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Enter a valid Bank Name");
                 }
                 else if(userProfile.B_NAME == null || userProfile.B_NAME.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Enter a valid Branch Name");
                 }
                 else if(userProfile.AC_HOLDER_NAME == null || userProfile.AC_HOLDER_NAME.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Enter a valid Account Holder Name");
                 }
                 else if(userProfile.AC_NUM == null || userProfile.AC_NUM.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Enter a valid account number");
                 }
                 else if(userProfile.IFSC_CODE == null || userProfile.IFSC_CODE.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Enter a valid IFSC Code");
                 }
                 else if(userProfile.PASSBOOK == null || userProfile.PASSBOOK.trim().isEmpty()){
                 DisplayMessageUtil.error(view.getContext(), "Select a valid Passbook");
                 }
                 else{
                 }**/
                DisplayMessageUtil.loading(view.getContext());
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = "";
                try {
                    json = ow.writeValueAsString(userProfile);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                profileRepository.apiServices.updateBankDetails(aadhaar, pan, passbook, getTextPlain(json))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res->{
                            if(res.getResponse_code().equals(1)){
                                DisplayMessageUtil.success(view.getContext(), res.getMessage());
                            }
                            else{
                                DisplayMessageUtil.error(view.getContext(), res.getMessage());
                            }
                        }, err->DisplayMessageUtil.error(view.getContext(), err.getMessage()));
            }
        }
    }

    public void updateSettingsDetailsData(View view){
        if(old_pass==null || old_pass.trim().isEmpty()){
            MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Old Password is Wrong", R.drawable.warning);
        }
        else if(new_pass==null || new_pass.length()<5){
            MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Enter a valid new Password eg: @#344bces^3 etc", R.drawable.warning);
        }
        else if(confirm_pass == null || !confirm_pass.equals(new_pass)){
            MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Enter a valid new Password doesn't match.", R.drawable.warning);
        }
        else{
            profileRepository.updateMyPassword(view.getContext(),user, new_pass, old_pass);
        }

    }

    public void checkM_pinStatus(TaskListener<SystemResponse<TPinResponse>> taskListener){
        if(Accessable.isAccessable()){
            taskListener.onLoading();
            profileRepository.apiServices.checkMpinStatus("check_m_pin")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(taskListener::onResponse, err-> taskListener.onError(err.getLocalizedMessage()));
        }
    }


    public void sendOtpFor(View view){
            OTP_TIMES++;
            DisplayMessageUtil.loading(view.getContext());
            profileRepository.apiServices.sendOTPForMpin(String.valueOf(OTP_TIMES),"otp_mpin")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res->{
                        DisplayMessageUtil.dismissDialog();
                        if(res.getStatus() && res.getResponse_code().equals(1)){
                            _pinState.setValue(M_PIN_STATE.VERIFICATION);
                            otpResponse = res.getReceivableData();
                        }
                        else{
                            DisplayMessageUtil.error(view.getContext(), res.getMessage());
                        }
                    }, err->DisplayMessageUtil.error(view.getContext(), err.getLocalizedMessage()));
    }

    public void setMyM_pin(View view){
        if(Accessable.isAccessable()){
         if(otpResponse == null || m_pin == null || otp.isEmpty() || m_pin.isEmpty()){
             DisplayMessageUtil.error(view.getContext(), "Provide a valid OTP");
         }
         else{
             DisplayMessageUtil.loading(view.getContext());
             profileRepository.apiServices.setAllMpin(m_pin ,otp, otpResponse.getOTPHASH(), "set_mpin")
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(res->{
                         DisplayMessageUtil.dismissDialog();
                         if(res.isStatus() && res.getResponse_code().equals(1)){
                             taskListener.onResponse(res);
                         }
                         else{
                             DisplayMessageUtil.error(view.getContext(), res.getMessage());
                         }
                     }, err->DisplayMessageUtil.error(view.getContext(), err.getLocalizedMessage()));
         }
        }
    }

    public void changeMpinWithOTP(View view){

        if(old_m_pin.isEmpty() || new_m_pin.isEmpty()){
            DisplayMessageUtil.error(view.getContext(), "Provide a valid M-PIN");
        }
        else if(otpResponse == null || otp.isEmpty()){
            DisplayMessageUtil.error(view.getContext(), "Provide a valid OTP");
        }
        else{
            DisplayMessageUtil.loading(view.getContext());
            profileRepository.apiServices.changeMyMpin(otp, otpResponse.getOTPHASH() ,old_m_pin ,new_m_pin, "change_mpin")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res->{
                        DisplayMessageUtil.dismissDialog();
                        if(res.isStatus() && res.getResponse_code().equals(1)){
                            taskListener.onResponse(res);
                        }
                        else{
                            DisplayMessageUtil.error(view.getContext(), res.getMessage());
                        }
                    }, err->DisplayMessageUtil.error(view.getContext(), err.getLocalizedMessage()));
        }
    }


    private RequestBody getTextPlain(String value){
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public void setAadhaar(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        aadhaar = MultipartBody.Part.createFormData("aadhaar", file.getName(), requestFile);
    }

    public void setPan(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        pan = MultipartBody.Part.createFormData("pan", file.getName(), requestFile);
    }

    public void setPassBook(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        passbook = MultipartBody.Part.createFormData("passbook", file.getName(), requestFile);
    }

    public enum M_PIN_STATE{
        CREATION,
        VERIFICATION
    }
}
