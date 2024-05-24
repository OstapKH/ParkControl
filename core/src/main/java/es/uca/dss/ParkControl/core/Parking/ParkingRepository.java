package es.uca.dss.ParkControl.core.Parking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingRepository  {
    void save(Parking parking);
    Optional<Parking> findById(UUID id);
    List<Parking> findAll();
    void deleteById(UUID id);
}
