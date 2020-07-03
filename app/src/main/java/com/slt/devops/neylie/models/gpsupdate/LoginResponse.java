package com.slt.devops.neylie.models.gpsupdate;

public class LoginResponse {

    private boolean error;
    private String message;
    private String data;

    public LoginResponse(boolean error, String message, String user) {
        this.error = error;
        this.message = message;
        this.data = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return data;
    }

}

