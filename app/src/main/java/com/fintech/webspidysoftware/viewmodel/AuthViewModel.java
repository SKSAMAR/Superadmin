package com.fintech.webspidysoftware.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fintech.webspidysoftware.data.db.entities.User;
import com.fintech.webspidysoftware.data.network.responses.RegularResponse;
import com.fintech.webspidysoftware.data.repositories.UserRepository;
import com.fintech.webspidysoftware.listeners.AuthListener;
import com.fintech.webspidysoftware.listeners.AuthoriseListener;
import com.fintech.webspidysoftware.util.MyAlertUtils;
import com.fintech.webspidysoftware.util.ViewUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AuthViewModel extends ViewModel {

    public UserRepository userRepository;

    @Inject
    public AuthViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String referal_code = "";
    public String partner_id = "";
    public String mobile = "";
    public String password = "";
    public AuthListener authListener = null;

    public AuthoriseListener<RegularResponse> authoriseListener;
    public String first_name = "";
    public String last_name = null;
    public String email = null;
    public String editOtp = null;

    public String password_two = null;
    public String password_one = null;

    public void onLoginButtonClick(View view) {
        authListener.onStarted();
        if (mobile.trim().isEmpty()|| password == null || password.trim().isEmpty()) {
            if(mobile.trim().length()<10){
                authListener.onFailure("Enter valid Mobile Number");
            }else {
                authListener.onFailure("Check  Mobile Number and Password Again..");
            }return;
        }
        //Success
        userRepository.userLogin(view.getContext(), mobile, password,authListener);
    }


    public void onSignupButtonClick(View view) {

        if (first_name == null|| first_name.isEmpty()){

            authListener.onFailure("Provide a valid first name");
        }
        else if(last_name == null || last_name.isEmpty()){
            authListener.onFailure("Provide a valid last name");
        }
        else if(mobile == null || mobile.isEmpty() || mobile.length()<10) {
            authListener.onFailure("Provide a valid mobile");
        }
        else if(email == null || email.isEmpty() || !ViewUtils.isEmailValid(email)){
            authListener.onFailure("Provide a valid email");
        }
        else{
            authListener.onStarted();
            userRepository.sendSignUpOtp(mobile, email, authListener);
        }

    }

    public void onOTPButtonClick(View view){
        if(editOtp == null || editOtp.isEmpty()){
            authListener.onFailure("Provide OTP");
        }
        else{
            authListener.displayMessage("9900");
        }
    }


    public LiveData<User> getLoggedInUser(){
        return userRepository.getSignedInUser();
    }


    public void onSignupPageChange(View view){
        authListener.onChangeFragmentPage("register");
    }

    public void onSigninPageChange(View view){
        authListener.onChangeFragmentPage("sign_in");
    }


    public void onForgotPasswordPageChange(View view){
        authListener.onChangeFragmentPage("forgot");
    }



    public void onCreateAccountButtonClick(View view){
        if(password_one == null  || password_one.isEmpty() || password_two == null || password_two.isEmpty()){
            authListener.onFailure("Enter both passwords.");
        }
        else{
            if(!password_one.equals(password_two)){
                authListener.onFailure("Both passwords don't match.");
            }
            else {
                authListener.onStarted();
                userRepository.userSignup(first_name,last_name,mobile,email,password_one, editOtp, referal_code, authListener ,authoriseListener);
            }
        }
    }


    public void onForgotPasswordButtonClick(View view){
        userRepository.forgotMyPassword(authListener, mobile);
    }

    public void onPasswordChangeButtonClick(View view){

        if(password_one==null){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter a valid password");
        }
        else if(password_one.isEmpty() || password_one.length()<5){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter a valid password more the 5 character");
        }
        else if(password_two==null){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter a valid password");
        }
        else if(password_two.isEmpty() || password_two.length()<5){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter a valid password more the 5 character");
        }
        else if(!password_one.equals(password_two)){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Password don't match");
        }
        else if(editOtp == null || editOtp.trim().isEmpty()){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter a valid otp");
        }
        else{
            userRepository.apiServices.changeMyPasswordStart(mobile, editOtp, password_one)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RegularResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            authListener.onStarted();
                        }

                        @Override
                        public void onNext(@NonNull RegularResponse response) {
                            authListener.otp_verification(response);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            authListener.onFailure("Failed due to\n"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
