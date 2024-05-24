package es.uca.dss.ParkControl.api_http.Controllers.RequestBodies;

import java.util.UUID;

public class VehicleExitRequestBody {
    String registrationNumber;
    UUID parkingId;
    UUID ticketId;

    public VehicleExitRequestBody(String registrationNumber, UUID parkingId, UUID ticketId) {
        this.registrationNumber = registrationNumber;
        this.parkingId = parkingId;
        this.ticketId = ticketId;
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

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }
}
