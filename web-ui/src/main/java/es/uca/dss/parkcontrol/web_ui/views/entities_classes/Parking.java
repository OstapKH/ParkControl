package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.util.UUID;

public class Parking {
    private UUID id;
    private String name;
    private int maxNumberOfSpaces;
    private String zipCode;
    private int currentAvailableNumberOfSpaces;

    public Parking(UUID id, String name, int maxNumberOfSpaces, String zipCode, int currentAvailableNumberOfSpaces) {
        this.id = id;
        this.name = name;
        this.maxNumberOfSpaces = maxNumberOfSpaces;
        this.zipCode = zipCode;
        this.currentAvailableNumberOfSpaces = currentAvailableNumberOfSpaces;
    }
    public Parking() {
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

    public int getCurrentAvailableNumberOfSpaces() {
        return currentAvailableNumberOfSpaces;
    }

    public void setCurrentAvailableNumberOfSpaces(int currentAvailableNumberOfSpaces) {
        this.currentAvailableNumberOfSpaces = currentAvailableNumberOfSpaces;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
