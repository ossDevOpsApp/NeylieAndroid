package com.slt.devops.neylie.models.gpsupdate;

import java.util.List;

public class EquipmentResponse {

    private boolean error;
    private List<Equipment> data;

    public EquipmentResponse(boolean error, List<Equipment> data) {
        this.error = error;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public List<Equipment> getEquipment() {
        return data;
    }
}
