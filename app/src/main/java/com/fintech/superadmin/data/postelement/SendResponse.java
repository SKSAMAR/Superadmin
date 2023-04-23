package com.fintech.superadmin.data.postelement;

public class SendResponse<T> {
    String event;
    T information;
    public SendResponse(String event, T information) {
        this.event = event;
        this.information = information;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public T getInformation() {
        return information;
    }

    public void setInformation(T information) {
        this.information = information;
    }
}
