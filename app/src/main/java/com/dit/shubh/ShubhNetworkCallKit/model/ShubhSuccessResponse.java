package com.dit.shubh.ShubhNetworkCallKit.model;

import java.io.Serializable;


public class ShubhSuccessResponse implements Serializable {

    private String status;
    private String message;
    private String response;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    @Override
    public String toString() {
        return "SuccessResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
