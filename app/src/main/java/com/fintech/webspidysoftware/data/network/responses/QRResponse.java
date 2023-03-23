package com.fintech.webspidysoftware.data.network.responses;

import com.fintech.webspidysoftware.data.model.VirtualAccountModel;

import java.io.Serializable;

public class QRResponse implements Serializable {
    public VirtualAccountModel vpa;
    public String qr_image;
}