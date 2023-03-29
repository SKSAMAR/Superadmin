package com.fintech.kkpayments.data.network.responses;

import com.fintech.kkpayments.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}