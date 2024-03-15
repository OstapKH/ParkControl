package es.uca.dss.ParkControl.core.Parking;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Parking {
    private UUID id;
    private String name;
    private String zipCode;
    private int maxNumberOfSpaces;
    private int currentAvailableNumberOfSpaces;
    private List<Vehicle> allocatedVehicles;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMaxNumberOfSpaces() {
        return maxNumberOfSpaces;
    }

    public void setMaxNumberOfSpaces(int maxNumberOfSpaces) {
        this.maxNumberOfSpaces = maxNumberOfSpaces;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getCurrentAvailableNumberOfSpaces() {
        return currentAvailableNumberOfSpaces;
    }

    public void setCurrentAvailableNumberOfSpaces(int currentAvailableNumberOfSpaces) {
        this.currentAvailableNumberOfSpaces = currentAvailableNumberOfSpaces;
    }

    public List<Vehicle> getAllocatedVehicles() {
        return allocatedVehicles;
    }

    public void setAllocatedVehicles(List<Vehicle> allocatedVehicles) {
        this.allocatedVehicles = allocatedVehicles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parking parking)) return false;
        return Objects.equals(id, parking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
