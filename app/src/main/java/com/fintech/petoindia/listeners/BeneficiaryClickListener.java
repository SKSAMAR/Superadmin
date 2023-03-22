package com.fintech.petoindia.listeners;

import android.view.View;

import com.fintech.petoindia.data.network.responses.BeneficiaryBank;

public interface BeneficiaryClickListener {
    void onItemClicked(View view, BeneficiaryBank model);

    void onMoreClickListener(View view, BeneficiaryBank model);
}
