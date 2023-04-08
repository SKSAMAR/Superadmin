package com.fintech.paytoindia.data.network.responses;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.String;

public class PaytmTokenInformation implements Serializable {
  private String mkey;

  private Response response;

  private String mid;

  public String getMkey() {
    return this.mkey;
  }

  public void setMkey(String mkey) {
    this.mkey = mkey;
  }

  public Response getResponse() {
    return this.response;
  }

  public void setResponse(Response response) {
    this.response = response;
  }

  public String getMid() {
    return this.mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public static class Response implements Serializable {
    private Head head;

    private Body body;

    public Head getHead() {
      return this.head;
    }

    public void setHead(Head head) {
      this.head = head;
    }

    public Body getBody() {
      return this.body;
    }

    public void setBody(Body body) {
      this.body = body;
    }

    public static class Head implements Serializable {
      private String responseTimestamp;

      private String signature;

      private String version;

      public String getResponseTimestamp() {
        return this.responseTimestamp;
      }

      public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
      }

      public String getSignature() {
        return this.signature;
      }

      public void setSignature(String signature) {
        this.signature = signature;
      }

      public String getVersion() {
        return this.version;
      }

      public void setVersion(String version) {
        this.version = version;
      }
    }

    public static class Body implements Serializable {
      private Boolean authenticated;

      private String txnToken;

      private Boolean isPromoCodeValid;

      private ResultInfo resultInfo;

      public Boolean getAuthenticated() {
        return this.authenticated;
      }

      public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
      }

      public String getTxnToken() {
        return this.txnToken;
      }

      public void setTxnToken(String txnToken) {
        this.txnToken = txnToken;
      }

      public Boolean getIsPromoCodeValid() {
        return this.isPromoCodeValid;
      }

      public void setIsPromoCodeValid(Boolean isPromoCodeValid) {
        this.isPromoCodeValid = isPromoCodeValid;
      }

      public ResultInfo getResultInfo() {
        return this.resultInfo;
      }

      public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
      }

      public static class ResultInfo implements Serializable {
        private String resultStatus;

        private String resultCode;

        private String resultMsg;

        public String getResultStatus() {
          return this.resultStatus;
        }

        public void setResultStatus(String resultStatus) {
          this.resultStatus = resultStatus;
        }

        public String getResultCode() {
          return this.resultCode;
        }

        public void setResultCode(String resultCode) {
          this.resultCode = resultCode;
        }

        public String getResultMsg() {
          return this.resultMsg;
        }

        public void setResultMsg(String resultMsg) {
          this.resultMsg = resultMsg;
        }
      }

      @Override
      public String toString() {
        return "Body{" +
                "authenticated=" + authenticated +
                ", txnToken='" + txnToken + '\'' +
                ", isPromoCodeValid=" + isPromoCodeValid +
                ", resultInfo=" + resultInfo +
                '}';
      }
    }
  }

  @Override
  public String toString() {
    return "TokenInformation{" +
            "mkey='" + mkey + '\'' +
            ", response=" + response +
            ", mid='" + mid + '\'' +
            '}';
  }
}
