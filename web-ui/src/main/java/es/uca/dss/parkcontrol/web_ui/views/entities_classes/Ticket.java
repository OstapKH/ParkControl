package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private UUID id;

    private Parking parking;

    private Vehicle vehicle;

    private Plan plan;

    private LocalDateTime dateOfIssue;
    private LocalDateTime dateOfPayment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public LocalDateTime getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDateTime dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDateTime getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDateTime dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
}
