package com.fintech.kkpayments.util;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SetFragmentUtils {

    public static void setFragment(Fragment fragment, View view){
        FragmentTransaction fragmentTransaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view.getId(),fragment);
        fragmentTransaction.commit();
    }
}
