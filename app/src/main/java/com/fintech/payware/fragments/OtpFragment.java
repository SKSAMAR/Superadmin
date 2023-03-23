package com.fintech.payware.fragments;

import android.app.ProgressDialog;
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

import com.fintech.payware.R;
import com.fintech.payware.data.network.responses.AuthResponse;
import com.fintech.payware.data.network.responses.RegularResponse;
import com.fintech.payware.databinding.FragmentOtpBinding;
import com.fintech.payware.listeners.AuthListener;
import com.fintech.payware.listeners.AuthoriseListener;
import com.fintech.payware.util.DisplayMessageUtil;
import com.fintech.payware.util.ViewUtils;
import com.fintech.payware.viewmodel.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OtpFragment extends Fragment implements AuthListener, AuthoriseListener<RegularResponse> {

    private FragmentOtpBinding binding;
    private FragmentContainerView fragmentContainerView;
    AuthViewModel authviewModel;
    ProgressDialog dialog;


    public OtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_otp, container, false);
        binding.OtpSection.setVisibility(View.VISIBLE);
        binding.createPasswordSection.setVisibility(View.GONE);
        fragmentContainerView = requireActivity().findViewById(R.id.ScreenFrame);
        authviewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        binding.setMyviewmodel(authviewModel);
        authviewModel.authListener = this;
        authviewModel.authoriseListener = this;
        dialog = new ProgressDialog(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStarted() {
        dialog.setMessage("Processing, please wait.");
        dialog.show();
    }

    @Override
    public void onSuccess(AuthResponse authResponse) {
        dialog.dismiss();
    }

    @Override
    public void onFailure(String message) {
        dialog.dismiss();
        ViewUtils.showToast(requireActivity(), message);
    }

    @Override
    public void onChangeFragmentPage(String fragmentName) {

    }

    @Override
    public void displayMessage(String message) {
        if(message.equals("9900")){
            binding.OtpSection.setVisibility(View.GONE);
            binding.createPasswordSection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void otp_verification(RegularResponse response) {

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentContainerView.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onAuth(RegularResponse data) {
        dialog.dismiss();
        if(data.isStatus() && data.getResponse_code().equals(1)){
            setFragment(new SignInFragment());
        }
        else{
            binding.OtpSection.setVisibility(View.VISIBLE);
            binding.createPasswordSection.setVisibility(View.GONE);
            DisplayMessageUtil.error(requireActivity(), data.getMessage());
        }
    }
}