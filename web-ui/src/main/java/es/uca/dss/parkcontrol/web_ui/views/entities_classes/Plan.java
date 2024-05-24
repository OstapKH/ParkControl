package es.uca.dss.parkcontrol.web_ui.views.entities_classes;


import java.util.UUID;


public class Plan {
    private UUID id;

    private String name;

    private double price;

    private PlanType planType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlanName() {
        return name;
    }

    public void setPlanName(String planName) {
        this.name = planName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }
}