package es.uca.dss.ParkControl.core.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryVehicleRepository implements VehicleRepository{
    private List<Vehicle> vehicles = new ArrayList<>();

    @Override
    public void save(Vehicle vehicle) {
        UUID id = vehicle.getId();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equals(id)) {
                vehicles.set(i, vehicle);
                return;
            }
        }
        vehicles.add(vehicle);
    }

    @Override
    public Vehicle findById(UUID id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public Vehicle findByRegistrationNumber(String registrationNumber) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getRegistrationNumber().equals(registrationNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicles;
    }

    @Override
    public void deleteById(UUID id) {
        vehicles.removeIf(vehicle -> vehicle.getId().equals(id));
    }
}
