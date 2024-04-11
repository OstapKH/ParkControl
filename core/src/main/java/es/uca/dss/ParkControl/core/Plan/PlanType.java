package es.uca.dss.ParkControl.core.Plan;

public enum PlanType {
    MINUTES(0.03),
    HOURS(0.5),
    DAYS(4),
    WEEKS(25);

    private final double value;

    PlanType(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
