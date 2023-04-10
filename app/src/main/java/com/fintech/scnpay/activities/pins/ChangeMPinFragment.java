package com.fintech.scnpay.activities.pins;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.scnpay.R;
import com.fintech.scnpay.databinding.FragmentChangeMPinBinding;
import com.fintech.scnpay.util.Accessable;
import com.fintech.scnpay.viewmodel.ProfileViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChangeMPinFragment extends Fragment {

    ProfileViewModel viewModel;
    FragmentChangeMPinBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_m_pin, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
        if(savedInstanceState!=null){
            onViewStateRestored(savedInstanceState);
        }
        manageState();
        setClick();
    }


    private void setClick(){

        binding.mPin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.mPin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin1.requestFocus();
                }
            }
        });
        binding.mPin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin2.requestFocus();
                }
            }
        });
        binding.mPin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin3.requestFocus();
                }
            }
        });


        binding.mPin5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.mPin6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin7.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin5.requestFocus();
                }
            }
        });
        binding.mPin7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin8.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin6.requestFocus();
                }
            }
        });
        binding.mPin8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin7.requestFocus();
                }
            }
        });
        binding.continueButton.setOnClickListener(view->{
            if(binding.mPin1.getText().toString().trim().isEmpty()){
                binding.mPin1.requestFocus();
            }
            else if(binding.mPin2.getText().toString().trim().isEmpty()){
                binding.mPin2.requestFocus();
            }
            else if(binding.mPin3.getText().toString().trim().isEmpty()){
                binding.mPin3.requestFocus();
            }
            else if(binding.mPin4.getText().toString().trim().isEmpty()){
                binding.mPin4.requestFocus();
            }
            else{
                String m_pin = binding.mPin1.getText().toString().trim()+binding.mPin2.getText().toString().trim()+binding.mPin3.getText().toString().trim()+binding.mPin4.getText().toString().trim();
                String c_m_pin = binding.mPin5.getText().toString().trim()+binding.mPin6.getText().toString().trim()+binding.mPin7.getText().toString().trim()+binding.mPin8.getText().toString().trim();
                if(Accessable.isAccessable()){
                        viewModel.old_m_pin = m_pin;
                        viewModel.new_m_pin = c_m_pin;
                        viewModel.sendOtpFor(view);
//                        viewModel.changeMpin(view);
                }
            }

        });

        binding.forgotMPin.setOnClickListener(view-> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.my_fragment_container, new CreateMPinFragment())
                .commit());

    }

    private void manageState(){

        viewModel.pinState.observe(getViewLifecycleOwner(), state->{
            if(state == ProfileViewModel.M_PIN_STATE.CREATION){
                binding.pinSection.setVisibility(View.VISIBLE);
                binding.otpSection.setVisibility(View.GONE);
            }
            else if(state == ProfileViewModel.M_PIN_STATE.VERIFICATION){
                binding.pinSection.setVisibility(View.GONE);
                binding.otpSection.setVisibility(View.VISIBLE);
            }
        });
    }
}