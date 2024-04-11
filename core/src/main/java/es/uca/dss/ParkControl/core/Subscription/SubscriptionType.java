package es.uca.dss.ParkControl.core.Subscription;

public enum SubscriptionType {
    WEEK(3.0),
    MONTH(10.0),
    QUARTER(25.0);

    private final double value;

    SubscriptionType(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
