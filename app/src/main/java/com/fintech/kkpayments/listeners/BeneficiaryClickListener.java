package com.fintech.kkpayments.listeners;

import android.view.View;

import com.fintech.kkpayments.data.network.responses.BeneficiaryBank;

public interface BeneficiaryClickListener {
    void onItemClicked(View view, BeneficiaryBank model);

    void onMoreClickListener(View view, BeneficiaryBank model);
}
