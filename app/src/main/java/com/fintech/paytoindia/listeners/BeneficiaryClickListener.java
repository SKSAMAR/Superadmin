package com.fintech.paytoindia.listeners;

import android.view.View;

import com.fintech.paytoindia.data.network.responses.BeneficiaryBank;

public interface BeneficiaryClickListener {
    void onItemClicked(View view, BeneficiaryBank model);

    void onMoreClickListener(View view, BeneficiaryBank model);
}
