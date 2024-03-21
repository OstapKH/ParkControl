package es.uca.dss.ParkControl.core.Vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleRepository {
    void save(Vehicle vehicle);
    Vehicle findById(UUID id);
    Vehicle findByRegistrationNumber(String registrationNumber);
    List<Vehicle> findAll();
    void deleteById(UUID id);
}
