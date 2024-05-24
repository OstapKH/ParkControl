package es.uca.dss.ParkControl.api_http.ParkingEntity;

import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Parking.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SpringParkingAdapterRepository implements ParkingRepository {
    private final SpringParkingJpaRepository springParkingJpaRepository;

    @Autowired
    public SpringParkingAdapterRepository(SpringParkingJpaRepository springParkingJpaRepository) {
        this.springParkingJpaRepository = springParkingJpaRepository;
    }

    @Override
    public void save(Parking parking) {
        springParkingJpaRepository.save(parking);
    }

    @Override
    public Optional<Parking> findById(UUID id) {
        return springParkingJpaRepository.findById(id);
    }

    @Override
    public List<Parking> findAll() {
        return springParkingJpaRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        springParkingJpaRepository.deleteById(id);
    }
}
