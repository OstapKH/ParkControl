package es.uca.dss.ParkControl.api_http.ParkingEntity;

import es.uca.dss.ParkControl.core.Parking.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface SpringParkingJpaRepository extends JpaRepository<Parking, UUID> {

}
