package es.uca.dss.ParkControl.core.Parking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryParkingRepository implements ParkingRepository{
    private List<Parking> parkings;

    public InMemoryParkingRepository() {
        this.parkings = new ArrayList<>();
    }

    @Override
    public void save(Parking parking) {
        UUID id = parking.getId();
        for (int i = 0; i < parkings.size(); i++) {
            if (parkings.get(i).getId().equals(id)) {
                parkings.set(i, parking); // Update existing parking entry
                return;
            }
        }
        parkings.add(parking); // If parking with given ID doesn't exist, add it as a new entry
    }
    @Override
    public Optional<Parking> findById(UUID id) {
        for (Parking parking : parkings) {
            if (parking.getId().equals(id)) {
                return Optional.of(parking);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Parking> findAll() {
        return parkings;
    }
    @Override
    public void deleteById(UUID id) {
        parkings.removeIf(parking -> parking.getId().equals(id));
    }
}