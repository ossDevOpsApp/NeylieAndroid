package com.slt.devops.neylie.models.gpsupdate;

public class User {


    private String SID, NAME, RTOM, LEVEL, CONTACTNO;

    public User(String sid,  String name,String rtom, String level, String contact) {
        this.SID = sid;
        this.NAME = name;
        this.RTOM = rtom;
        this.LEVEL = level;
        this.CONTACTNO = contact;
    }

    public  String getId() {
        return SID;
    }

    public String getName() {
        return NAME;
    }

    public String getRtom() {
        return RTOM;
    }

    public String getLevel() {
        return LEVEL;
    }

    public String getContact() {
        return CONTACTNO;
    }
}
