package com.fintech.paytoindia.data.network.responses;

import java.util.List;

public class PlanType {

    List<BrowsePlanResponse> FULLTT;
    List<BrowsePlanResponse> TOPUP;
    List<BrowsePlanResponse> DATAPLAN;
    List<BrowsePlanResponse> RATECUTTER;
    List<BrowsePlanResponse> ROAMING;

    public PlanType(List<BrowsePlanResponse> FULLTT, List<BrowsePlanResponse> TOPUP, List<BrowsePlanResponse> DATAPLAN, List<BrowsePlanResponse> RATECUTTER, List<BrowsePlanResponse> ROAMING) {
        this.FULLTT = FULLTT;
        this.TOPUP = TOPUP;
        this.DATAPLAN = DATAPLAN;
        this.RATECUTTER = RATECUTTER;
        this.ROAMING = ROAMING;
    }

    public List<BrowsePlanResponse> getFULLTT() {
        return FULLTT;
    }

    public void setFULLTT(List<BrowsePlanResponse> FULLTT) {
        this.FULLTT = FULLTT;
    }

    public List<BrowsePlanResponse> getTOPUP() {
        return TOPUP;
    }

    public void setTOPUP(List<BrowsePlanResponse> TOPUP) {
        this.TOPUP = TOPUP;
    }

    public List<BrowsePlanResponse> getDATAPLAN() {
        return DATAPLAN;
    }

    public void setDATAPLAN(List<BrowsePlanResponse> DATAPLAN) {
        this.DATAPLAN = DATAPLAN;
    }

    public List<BrowsePlanResponse> getRATECUTTER() {
        return RATECUTTER;
    }

    public void setRATECUTTER(List<BrowsePlanResponse> RATECUTTER) {
        this.RATECUTTER = RATECUTTER;
    }

    public List<BrowsePlanResponse> getROAMING() {
        return ROAMING;
    }

    public void setROAMING(List<BrowsePlanResponse> ROAMING) {
        this.ROAMING = ROAMING;
    }

    @Override
    public String toString() {
        return "PlanType{" +
                "FULLTT=" + FULLTT +
                ", TOPUP=" + TOPUP +
                ", DATAPLAN=" + DATAPLAN +
                ", RATECUTTER=" + RATECUTTER +
                ", ROAMING=" + ROAMING +
                '}';
    }
}
