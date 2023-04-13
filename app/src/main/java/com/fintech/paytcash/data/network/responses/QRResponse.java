package com.fintech.paytcash.data.network.responses;

import com.fintech.paytcash.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}