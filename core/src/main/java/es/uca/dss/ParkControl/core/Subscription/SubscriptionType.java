package es.uca.dss.ParkControl.core.Subscription;

import java.util.Objects;
import java.util.UUID;

// Instead of using ENUM for subscription type, it was decided to use class as it allows more flexibility, thus User of the system can create new subscription types
public class SubscriptionType {
    private UUID id;
    private String name;
    double price;

    public SubscriptionType() {
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionType that)) return false;
        return Double.compare(price, that.price) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

}
