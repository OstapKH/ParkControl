package es.uca.dss.ParkControl.core.Vehicle;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {
    private VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public void createVehicle(Vehicle vehicle) {
        repository.save(vehicle);
    }

    public Vehicle getVehicle(UUID id) {
        return repository.findById(id);
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public void deleteVehicle(UUID id) {
        repository.deleteById(id);
    }

    public Vehicle getVehicleByRegistrationNumber(String registrationNumber) {
        return repository.findByRegistrationNumber(registrationNumber);
    }
}
