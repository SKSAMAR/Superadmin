package com.fintech.paytoindia.data.network.responses;

import com.fintech.paytoindia.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}