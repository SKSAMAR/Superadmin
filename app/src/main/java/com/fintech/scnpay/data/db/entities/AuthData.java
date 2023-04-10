package com.fintech.scnpay.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "auth_data")
public class AuthData{

  public  int CURRENT_USER_ID = 0;

  @PrimaryKey(autoGenerate = false)
  public int uId = CURRENT_USER_ID;

  private Boolean Status;

  private String rs_code;

  private String Token;

  private String User_ID;

  private String User_Exist;

  private Integer Login_Auth;

  private String User_Type;

  public Boolean getStatus() {
    return this.Status;
  }

  public void setStatus(Boolean Status) {
    this.Status = Status;
  }

  public String getRs_code() {
    return this.rs_code;
  }

  public void setRs_code(String rs_code) {
    this.rs_code = rs_code;
  }

  public String getToken() {
    return this.Token;
  }

  public void setToken(String Token) {
    this.Token = Token;
  }

  public String getUser_ID() {
    return this.User_ID;
  }

  public void setUser_ID(String User_ID) {
    this.User_ID = User_ID;
  }

  public String getUser_Exist() {
    return this.User_Exist;
  }

  public void setUser_Exist(String User_Exist) {
    this.User_Exist = User_Exist;
  }

  public Integer getLogin_Auth() {
    return this.Login_Auth;
  }

  public void setLogin_Auth(Integer Login_Auth) {
    this.Login_Auth = Login_Auth;
  }

  public String getUser_Type() {
    return this.User_Type;
  }

  public void setUser_Type(String User_Type) {
    this.User_Type = User_Type;
  }

  @Override
  public String toString() {
    return "AuthData{" +
            ", Status=" + Status +
            ", rs_code='" + rs_code + '\'' +
            ", Token='" + Token + '\'' +
            ", User_ID='" + User_ID + '\'' +
            ", User_Exist='" + User_Exist + '\'' +
            ", Login_Auth=" + Login_Auth +
            ", User_Type='" + User_Type + '\'' +
            '}';
  }
}
