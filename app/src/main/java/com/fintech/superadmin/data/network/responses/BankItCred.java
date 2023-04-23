package com.fintech.superadmin.data.network.responses;

import java.io.Serializable;

public class BankItCred implements Serializable {
    String developer_id;
    String password;

    public BankItCred(String developer_id, String password) {
        this.developer_id = developer_id;
        this.password = password;
    }

    public String getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(String developer_id) {
        this.developer_id = developer_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
