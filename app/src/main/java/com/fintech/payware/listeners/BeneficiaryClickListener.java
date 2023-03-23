package com.fintech.payware.listeners;

import android.view.View;

import com.fintech.payware.data.network.responses.BeneficiaryBank;

public interface BeneficiaryClickListener {
    void onItemClicked(View view, BeneficiaryBank model);

    void onMoreClickListener(View view, BeneficiaryBank model);
}
