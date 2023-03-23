package com.fintech.payware.data.network.responses;

import com.fintech.payware.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}