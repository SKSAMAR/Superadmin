package com.fintech.petoindia.data.network.responses;

import com.fintech.petoindia.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}