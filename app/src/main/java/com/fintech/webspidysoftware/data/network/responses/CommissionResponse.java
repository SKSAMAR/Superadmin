package com.fintech.webspidysoftware.data.network.responses;

public class CommissionResponse {
    String charge;
    String commission;
    String charge_commission;

    public CommissionResponse(String charge, String commission, String charge_commission) {
        this.charge = charge;
        this.commission = commission;
        this.charge_commission = charge_commission;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCharge_commission() {
        return charge_commission;
    }

    public void setCharge_commission(String charge_commission) {
        this.charge_commission = charge_commission;
    }
}
