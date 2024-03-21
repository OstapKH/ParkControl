package es.uca.dss.ParkControl.core.Subscription;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import java.time.LocalDate;
import java.util.UUID;

public class Subscription {
    private UUID id;

    private Vehicle vehicle;

    private LocalDate dateOfPurchase;

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

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}
