package com.fintech.superadmin.data.network.responses;

import com.fintech.superadmin.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}