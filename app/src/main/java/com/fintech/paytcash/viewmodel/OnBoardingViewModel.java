package com.fintech.paytcash.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.fintech.paytcash.data.repositories.OnBoardingRepository;
import com.fintech.paytcash.databinding.ActivityMicroAtmOnBoardBinding;
import com.fintech.paytcash.util.MyAlertUtils;
import com.fintech.paytcash.util.ViewUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class OnBoardingViewModel extends ViewModel {

    public ActivityMicroAtmOnBoardBinding binding;
    public String FIRST_NAME = null;
    public String LAST_NAME = null;
    public String SERIAL_NUMBER = null;
    public String SERIAL_NUMBER_PHOTO_NAME = null;
    public String ADDHAR_FRONT_NAME = null;
    public String ADDHAR_BACK_NAME = null;
    public String PAN_FRONT_NAME = null;
    public String PAN_BACK_NAME = null;
    public String MOBILE_NUMBER = null;
    public String EMAIL = null;
    public String STATUS = null;
    public String ADDHAR_NO = null;
    public String PAN_NO = null;


    public String SERIAL_NUMBER_PHOTO_ENCODE = null;
    public String ADDHAR_FRONT_ENCODE = null;
    public String ADDHAR_BACK_ENCODE = null;
    public String PAN_FRONT_ENCODE = null;
    public String PAN_BACK_ENCODE = null;

    public OnBoardingRepository repository;

    @Inject
    public OnBoardingViewModel(OnBoardingRepository repository){
        this.repository = repository;
    }

    public void startOnBoardMicroAtm(View view){
        if(FIRST_NAME==null || FIRST_NAME.isEmpty() || !ViewUtils.checkIfNameIsValid(FIRST_NAME)){
            warningScreen(view, "First Name");
        }
        else if(LAST_NAME==null || LAST_NAME.isEmpty() || !ViewUtils.checkIfNameIsValid(LAST_NAME)){
            warningScreen(view, "Last Name");
        }
        else if(MOBILE_NUMBER==null || MOBILE_NUMBER.isEmpty() || MOBILE_NUMBER.length()<10){
            warningScreen(view, "Mobile number");
        }
        else if(EMAIL==null || EMAIL.isEmpty() || !ViewUtils.isEmailValid(EMAIL)){
            warningScreen(view, "Email address");
        }
        else if(ADDHAR_FRONT_ENCODE==null || ADDHAR_FRONT_ENCODE.isEmpty()){
            warningScreen(view, "Front Aadhaar");
        }
        else if(ADDHAR_BACK_ENCODE==null || ADDHAR_BACK_ENCODE.isEmpty()){
            warningScreen(view, "Back Aadhaar");
        }
        else if(ADDHAR_NO==null || ADDHAR_NO.isEmpty() || ADDHAR_NO.length()<12){
            warningScreen(view, "Aadhaar Number");
        }
        else if(PAN_FRONT_ENCODE==null || PAN_FRONT_ENCODE.isEmpty()){
            warningScreen(view, "Front Pan");
        }
        else if(PAN_BACK_ENCODE==null || PAN_BACK_ENCODE.isEmpty()){
            warningScreen(view, "Back Pan");
        }
        else if(PAN_NO==null || PAN_NO.isEmpty()){
            warningScreen(view, "Pan Number");
        }
        else if(SERIAL_NUMBER_PHOTO_ENCODE==null || SERIAL_NUMBER_PHOTO_ENCODE.isEmpty()){
            warningScreen(view, "Atm Serial Photo");
        }
        else{
            repository.startOnBoarding(view, FIRST_NAME, LAST_NAME, EMAIL, MOBILE_NUMBER, ADDHAR_NO, PAN_NO, SERIAL_NUMBER, ADDHAR_FRONT_ENCODE, ADDHAR_BACK_ENCODE, PAN_FRONT_ENCODE, PAN_BACK_ENCODE, SERIAL_NUMBER_PHOTO_ENCODE);
        }

    }

    private void warningScreen(View view, String message){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide valid "+message);
        }

}
