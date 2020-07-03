package com.slt.devops.neylie.models.gpsupdate;

public class UpdateResponse {


    private boolean error;
    private String message;

    public UpdateResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}