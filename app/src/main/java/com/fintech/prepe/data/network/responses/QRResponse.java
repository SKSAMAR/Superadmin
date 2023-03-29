package com.fintech.prepe.data.network.responses;

import com.fintech.prepe.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}