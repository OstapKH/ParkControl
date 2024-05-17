package es.uca.dss.ParkControl.api_http.VehicleEntity;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import es.uca.dss.ParkControl.core.Vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpringVehicleAdapterRepository implements VehicleRepository {
    private final SpringVehicleJpaRepository springVehicleJpaRepository;

    @Autowired
    public SpringVehicleAdapterRepository(SpringVehicleJpaRepository springVehicleJpaRepository) {
        this.springVehicleJpaRepository = springVehicleJpaRepository;
    }

    @Override
    public void save(Vehicle vehicle) {
        springVehicleJpaRepository.save(vehicle);
    }

    @Override
    public Vehicle findById(UUID id) {
        return springVehicleJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Vehicle findByRegistrationNumber(String registrationNumber) {
        return springVehicleJpaRepository.findByRegistrationNumber(registrationNumber);
    }

    @Override
    public List<Vehicle> findAll() {
        return springVehicleJpaRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        springVehicleJpaRepository.deleteById(id);
    }
}