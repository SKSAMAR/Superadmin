package com.fintech.paytoindia.resultHandler;

public class FetchedData<T>{
    NetworkResponse response = NetworkResponse.EMPTY;
    T data;
    public FetchedData(NetworkResponse response, T data) {
        this.response = response;
        this.data = data;
    }

    public NetworkResponse getResponse() {
        return response;
    }

    public void setResponse(NetworkResponse response) {
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
