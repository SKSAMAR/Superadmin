package com.fintech.scnpay.data.network.responses;

import com.fintech.scnpay.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}