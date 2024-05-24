package es.uca.dss.ParkControl.api_http.Controllers.RequestBodies;

import java.util.UUID;

public class VehicleEnterRequestBody {
    String registrationNumber;
    UUID parkingId;

    public VehicleEnterRequestBody(String registrationNumber, UUID parkingId) {
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
