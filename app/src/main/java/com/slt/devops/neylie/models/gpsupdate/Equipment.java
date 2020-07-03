package com.slt.devops.neylie.models.gpsupdate;

public class Equipment {

    private String RTOM, LOCATION, TYPE, LATITUDE, LONGITUDE;

    public Equipment(String rtom, String location, String type, String lat, String lon) {
        this.RTOM = rtom;
        this.LOCATION = location;
        this.TYPE = type;
        this.LATITUDE = lat;
        this.LONGITUDE = lon;
    }

    public String getRtom() {
        return RTOM;
    }

    public String getLocation() {
        return LOCATION;
    }

    public String getType() {
        return TYPE;
    }

    public String getLatitude() {
        return LATITUDE;
    }

    public String getLontitude() {
        return LONGITUDE;
    }
}
