package com.fintech.webspidysoftware.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.webspidysoftware.R;
import com.fintech.webspidysoftware.data.network.responses.AuthResponse;
import com.fintech.webspidysoftware.data.network.responses.RegularResponse;
import com.fintech.webspidysoftware.databinding.FragmentForgotPasswordBinding;
import com.fintech.webspidysoftware.databinding.OtpScreenLayoutBinding;
import com.fintech.webspidysoftware.listeners.AuthListener;
import com.fintech.webspidysoftware.util.MyAlertUtils;
import com.fintech.webspidysoftware.util.ViewUtils;
import com.fintech.webspidysoftware.viewmodel.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ForgotPasswordFragment extends Fragment implements AuthListener {


    FragmentContainerView fragmentContainerView;
    FragmentForgotPasswordBinding binding;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_forgot_password ,container, false);
        fragmentContainerView = requireActivity().findViewById(R.id.ScreenFrame);
        AuthViewModel viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        binding.setMyviewmodel(viewModel);
        viewModel.authListener = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewUtils.setFocusable(binding.mobileNumber, requireActivity());
    }

    @Override
    public void onStarted() {
            MyAlertUtils.showProgressAlertDialog(requireActivity());
    }

    @Override
    public void onSuccess(AuthResponse authResponse) {
        if(authResponse.isStatus()){
            MyAlertUtils.showAlertDialog(requireContext(),"Success",authResponse.getMessage(),R.drawable.success);
        }
        else{
            MyAlertUtils.showAlertDialog(requireContext(),"Failed",authResponse.getMessage(),R.drawable.failed);
        }
    }

    @Override
    public void onFailure(String message) {
        MyAlertUtils.showServerAlertDialog(requireActivity(), message);
    }

    @Override
    public void onChangeFragmentPage(String fragmentName) {
        if(fragmentName.equals("sign_in")){
            setFragment(new SignInFragment());
        }
    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void otp_verification(RegularResponse response) {
        MyAlertUtils.dismissAlertDialog();
        if(response.status){
            setFragment(new ChangePasswordFragment());

        }else{
            MyAlertUtils.showAlertDialog(requireActivity(),"Result", response.getMessage(), R.drawable.warning);
        }
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentContainerView.getId(),fragment);
        fragmentTransaction.commit();
    }
}