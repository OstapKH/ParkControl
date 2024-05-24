package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.util.Objects;
import java.util.UUID;

public class SubscriptionType {
    private UUID id;

    private String name;
    private double price;

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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionType that)) return false;
        return Double.compare(price, that.price) == 0 && Objects.equals(name, that.name);
    }

    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}