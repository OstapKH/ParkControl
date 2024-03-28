package es.uca.dss.ParkControl.core.Vehicle;

import com.beust.jcommander.internal.Nullable;

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
