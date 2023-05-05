 package com.fintech.superadmin.activities.aeps.brandedComp;
 


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
 
 public class GetCommonBankListModel {
   @SerializedName("ID")
   @Expose
   private Integer iD;
   @SerializedName("NAME")
   @Expose
   private String nAME;
   @SerializedName("IIN")
   @Expose
   private Integer iIN;
   @SerializedName("Url")
   @Expose
   private String url;
   
   public String getUrl() {
/* 21 */     return this.url;
   }
   
   public void setUrl(String url) {
/* 25 */     this.url = url;
   }
   
   public Integer getID() {
/* 29 */     return this.iD;
   }
   
   public void setID(Integer iD) {
/* 33 */     this.iD = iD;
   }
   
   public String getNAME() {
/* 37 */     return this.nAME;
   }
   
   public void setNAME(String nAME) {
/* 41 */     this.nAME = nAME;
   }
   
   public Integer getIIN() {
/* 45 */     return this.iIN;
   }
   
   public void setIIN(Integer iIN) {
/* 49 */     this.iIN = iIN;
   }
 }