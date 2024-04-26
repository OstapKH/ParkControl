package es.uca.dss.ParkControl.core.Subscription;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Subscription {
    private UUID id;

    private Vehicle vehicle;

    private LocalDateTime dateOfPurchase;

    private SubscriptionType subscriptionType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDateTime dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}
