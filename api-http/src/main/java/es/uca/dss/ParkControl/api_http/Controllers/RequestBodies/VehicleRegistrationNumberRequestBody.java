package es.uca.dss.ParkControl.api_http.Controllers.RequestBodies;

public class VehicleRegistrationNumberRequestBody {
    String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
