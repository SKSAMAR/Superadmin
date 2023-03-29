package com.fintech.kkpayments.deer_adapter.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract  class BaseViewHolder<W extends View> extends RecyclerView.ViewHolder {
    View itemView;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public abstract View bind(W item);
}
