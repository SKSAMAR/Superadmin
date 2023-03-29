package com.fintech.prepe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintech.prepe.R;
import com.fintech.prepe.data.network.responses.AuthResponse;
import com.fintech.prepe.data.network.responses.RegularResponse;
import com.fintech.prepe.databinding.FragmentChangePasswordBinding;
import com.fintech.prepe.listeners.AuthListener;
import com.fintech.prepe.util.MyAlertUtils;
import com.fintech.prepe.viewmodel.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChangePasswordFragment extends Fragment implements AuthListener {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    AuthViewModel authviewModel;
    private FragmentContainerView fragmentContainerView;
    FragmentChangePasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_change_password, container, false);
        fragmentContainerView = requireActivity().findViewById(R.id.ScreenFrame);
        authviewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        binding.setMyviewmodel(authviewModel);
        authviewModel.authListener = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStarted() {
        MyAlertUtils.showProgressAlertDialog(requireActivity());
    }

    @Override
    public void onSuccess(AuthResponse authResponse) {

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
        else if(fragmentName.equals("forgot")){
            setFragment(new ForgotPasswordFragment());
        }
    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void otp_verification(RegularResponse response) {
        if(response.isStatus() && response.getResponse_code().equals(1)){
            MyAlertUtils.showAlertDialog(requireActivity(), "Result", response.message, R.drawable.success);
            setFragment(new SignInFragment());
            authviewModel.password_one = "";
            authviewModel.password_two = "";
            authviewModel.editOtp = "";
        }else{
            MyAlertUtils.showAlertDialog(requireActivity(), "Result", response.message, R.drawable.failed);
        }
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentContainerView.getId(),fragment);
        fragmentTransaction.commit();
    }
}