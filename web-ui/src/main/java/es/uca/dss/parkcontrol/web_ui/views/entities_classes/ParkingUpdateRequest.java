package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.util.UUID;

public class ParkingUpdateRequest {
    private String name;
    private int maxNumberOfSpaces;
    private String zipCode;

    public ParkingUpdateRequest() {
    }

    public ParkingUpdateRequest(String name, int maxNumberOfSpaces, String zipCode) {
        this.zipCode = zipCode;
        this.name = name;
        this.maxNumberOfSpaces = maxNumberOfSpaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumberOfSpaces() {
        return maxNumberOfSpaces;
    }

    public void setMaxNumberOfSpaces(int maxNumberOfSpaces) {
        this.maxNumberOfSpaces = maxNumberOfSpaces;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}