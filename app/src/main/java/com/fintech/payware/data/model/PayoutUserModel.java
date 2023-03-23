package com.fintech.payware.data.model;

public class PayoutUserModel {

        private String ID;
        private String NAME;
        private String BENEID;
        private String BANK_NAME;
        private String ACCOUNT;
        private String VERIFY_RESPONSE;
        private String IFSC;
        private String SEND_DATA;
        private String RESPONSE;
        private String DATE;
        private String STATUS;
        private String US_ID;

        public String getID() {
            return ID;
        }

        public String getNAME() {
            return NAME;
        }

        public String getBENEID() {
            return BENEID;
        }

        public String getBANK_NAME() {
            return BANK_NAME;
        }

        public String getACCOUNT() {
            return ACCOUNT;
        }

        public String getVERIFY_RESPONSE() {
            return VERIFY_RESPONSE;
        }

        public String getIFSC() {
            if(IFSC!=null){
                return IFSC.toUpperCase();
            }
            else{
                return null;
            }
        }

        public String getSEND_DATA() {
            return SEND_DATA;
        }

        public String getRESPONSE() {
            return RESPONSE;
        }

        public String getDATE() {
            return DATE;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public String getUS_ID() {
            return US_ID;
        }


        public void setID(String ID) {
            this.ID = ID;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public void setBENEID(String BENEID) {
            this.BENEID = BENEID;
        }

        public void setBANK_NAME(String BANK_NAME) {
            this.BANK_NAME = BANK_NAME;
        }

        public void setACCOUNT(String ACCOUNT) {
            this.ACCOUNT = ACCOUNT;
        }

        public void setVERIFY_RESPONSE(String VERIFY_RESPONSE) {
            this.VERIFY_RESPONSE = VERIFY_RESPONSE;
        }

        public void setIFSC(String IFSC) {
            this.IFSC = IFSC;
        }

        public void setSEND_DATA(String SEND_DATA) {
            this.SEND_DATA = SEND_DATA;
        }

        public void setRESPONSE(String RESPONSE) {
            this.RESPONSE = RESPONSE;
        }

        public void setDATE(String DATE) {
            this.DATE = DATE;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public void setUS_ID(String US_ID) {
            this.US_ID = US_ID;
        }

    @Override
    public String toString() {
        return "PayoutUserModel{" +
                "ID='" + ID + '\'' +
                ", NAME='" + NAME + '\'' +
                ", BENEID='" + BENEID + '\'' +
                ", BANK_NAME='" + BANK_NAME + '\'' +
                ", ACCOUNT='" + ACCOUNT + '\'' +
                ", VERIFY_RESPONSE='" + VERIFY_RESPONSE + '\'' +
                ", IFSC='" + IFSC + '\'' +
                ", SEND_DATA='" + SEND_DATA + '\'' +
                ", RESPONSE='" + RESPONSE + '\'' +
                ", DATE='" + DATE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", US_ID='" + US_ID + '\'' +
                '}';
    }
}
