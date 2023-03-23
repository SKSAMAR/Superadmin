package com.fintech.payware.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "report")
public class Report {
    public  int CURRENT_USER_ID = 0;

    @PrimaryKey(autoGenerate = false)
    public int uId = CURRENT_USER_ID;

    public long loginTimeStamp;

    public Report(long loginTimeStamp) {
        this.loginTimeStamp = loginTimeStamp;
    }

    public long getLoginTimeStamp() {
        return loginTimeStamp;
    }

    public void setLoginTimeStamp(long loginTimeStamp) {
        this.loginTimeStamp = loginTimeStamp;
    }
}
