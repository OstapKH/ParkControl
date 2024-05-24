package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.util.UUID;

public class ParkingEnterRequest {
    private String registrationNumber;
    private UUID parkingId;


    public ParkingEnterRequest(String registrationNumber, UUID parkingId) {
        this.registrationNumber = registrationNumber;
        this.parkingId = parkingId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public UUID getParkingId() {
        return parkingId;
    }

    public void setParkingId(UUID parkingId) {
        this.parkingId = parkingId;
    }
}
