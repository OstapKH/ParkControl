package es.uca.dss.ParkControl.api_http.VehicleEntity;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface SpringVehicleJpaRepository extends JpaRepository<Vehicle, UUID> {
    Vehicle findByRegistrationNumber(String registrationNumber);
}
