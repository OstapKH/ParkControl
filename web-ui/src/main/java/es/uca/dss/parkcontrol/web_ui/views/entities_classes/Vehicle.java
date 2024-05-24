package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.util.UUID;

public class Vehicle {
    private UUID id;

    private String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}