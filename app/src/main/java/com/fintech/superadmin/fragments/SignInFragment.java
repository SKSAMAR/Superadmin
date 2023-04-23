package com.fintech.superadmin.fragments;

import android.content.Intent;
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

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.HomeActivity;
import com.fintech.superadmin.data.network.responses.AuthResponse;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.databinding.FragmentSignInBinding;
import com.fintech.superadmin.listeners.AuthListener;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignInFragment extends Fragment implements AuthListener {


    private FragmentContainerView fragmentContainerView;
    private FragmentSignInBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment //
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        fragmentContainerView = requireActivity().findViewById(R.id.ScreenFrame);
        AuthViewModel authviewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        binding.setMyviewmodel(authviewModel);
        authviewModel.authListener = this;
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewUtils.setFocusable(binding.mobileNumber, requireActivity());
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentContainerView.getId(), fragment);
        fragmentTransaction.commit();
    }

    public void setFragmentLower(Fragment fragment, FragmentContainerView myFragmentContainerView) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(myFragmentContainerView.getId(), fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onStarted() {
        MyAlertUtils.showProgressAlertDialog(requireContext());
//          ViewUtils.showProgressBar(binding.MySignInProgress);
    }

    @Override
    public void onSuccess(AuthResponse authResponse) {
        if (authResponse.isStatus()) {
            Intent intent = new Intent(requireActivity(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyAlertUtils.dismissAlertDialog();
            startActivity(intent);
        } else {
            MyAlertUtils.dismissAlertDialog();
            ViewUtils.showToast(requireActivity(), authResponse.getMessage());
        }
    }

    @Override
    public void onFailure(String message) {
        MyAlertUtils.showServerAlertDialog(requireContext(), message);
    }

    @Override
    public void onChangeFragmentPage(String fragmentName) {
        if (fragmentName.equals("register")) {
            setFragment(new SignUpFragment());
        } else if (fragmentName.equals("forgot")) {
            setFragment(new ForgotPasswordFragment());
        }
    }

    @Override
    public void displayMessage(String message) {
        ViewUtils.showToast(this.requireActivity(), message);
    }

    @Override
    public void otp_verification(RegularResponse response) {

    }


}