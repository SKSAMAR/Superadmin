package com.fintech.paytcash.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.paytcash.R;
import com.fintech.paytcash.data.network.NetworkConnectionInterceptor;
import com.fintech.paytcash.data.network.responses.AuthResponse;
import com.fintech.paytcash.data.network.responses.RegularResponse;
import com.fintech.paytcash.databinding.FragmentSignUpBinding;
import com.fintech.paytcash.listeners.AuthListener;
import com.fintech.paytcash.util.MyAlertUtils;
import com.fintech.paytcash.util.ViewUtils;
import com.fintech.paytcash.viewmodel.AuthViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment implements AuthListener {

    private FragmentContainerView fragmentContainerView;

    @Inject
    NetworkConnectionInterceptor networkConnectionInterceptor;

    AuthViewModel authviewModel;
    FragmentSignUpBinding binding;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        fragmentContainerView = requireActivity().findViewById(R.id.ScreenFrame);
        authviewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        binding.setMyviewmodel(authviewModel);
        authviewModel.authListener = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewUtils.setFocusable(binding.firstNumber, requireActivity());
    }



    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentContainerView.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStarted() {
        MyAlertUtils.showProgressAlertDialog(requireContext());

    }

    @Override
    public void onSuccess(AuthResponse authResponse) {
        MyAlertUtils.dismissAlertDialog();
    }

    @Override
    public void onFailure(String message) {
        MyAlertUtils.showServerAlertDialog(requireContext(), message);
    }

    @Override
    public void onChangeFragmentPage(String fragmentName) {
        if(fragmentName.equals("sign_in")){
            setFragment(new SignInFragment());
        }
        else if(fragmentName.equals("forgot")){
            setFragment(new ForgotPasswordFragment());
        }
    }

    @Override
    public void displayMessage(String message) {
       // message = message.trim();
       // if(!message.equals("Good")){
       //     MyAlertUtils.showServerAlertDialog(requireContext(), message);
       // }
       // else{
       //     MyAlertUtils.dismissAlertDialog();
       //     setFragment(new OtpFragment());
       // }
    }

    @Override
    public void otp_verification(RegularResponse response) {
        if(response.status && !response.getResponse_code().equals(999)){
            MyAlertUtils.dismissAlertDialog();
            setFragment(new OtpFragment());
        }
    }

}