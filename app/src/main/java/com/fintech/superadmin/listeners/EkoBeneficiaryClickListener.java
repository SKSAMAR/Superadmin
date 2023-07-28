package com.fintech.superadmin.listeners;

import android.view.View;

import com.fintech.superadmin.data.eko.RecipientListItem;
import com.fintech.superadmin.data.network.responses.BeneficiaryBank;

public interface EkoBeneficiaryClickListener {
    void onItemClicked(View view, RecipientListItem model);

    void onMoreClickListener(View view, RecipientListItem model);
}
