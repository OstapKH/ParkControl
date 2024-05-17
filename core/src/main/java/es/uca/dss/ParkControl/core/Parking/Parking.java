package es.uca.dss.ParkControl.core.Parking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "parkings")
@EntityListeners(AuditingEntityListener.class)
public class Parking {
    @Id
    private UUID id;
    @Column(unique = true)
    private String name;
    private String zipCode;
    private int maxNumberOfSpaces;
    private int currentAvailableNumberOfSpaces;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> allocatedVehicles = new ArrayList<>();

    // getters and setters

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getMaxNumberOfSpaces() {
        return maxNumberOfSpaces;
    }

    public void setMaxNumberOfSpaces(int maxNumberOfSpaces) {
        this.maxNumberOfSpaces = maxNumberOfSpaces;
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
}