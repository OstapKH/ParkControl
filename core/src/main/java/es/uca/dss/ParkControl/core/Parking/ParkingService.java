package es.uca.dss.ParkControl.core.Parking;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingService {
    private ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public void saveParking(Parking parking) {
        parkingRepository.save(parking);
    }

    public Optional<Parking> getParkingById(UUID id) {
        return parkingRepository.findById(id);
    }

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public void deleteParking(UUID id) {
        parkingRepository.deleteById(id);
    }

    public void changeParkingName(UUID id, String newName) {
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isPresent()) {
            parking.get().setName(newName);
            parkingRepository.save(parking.get());
        } else {
            System.out.println("Parking not found with id: " + id);
        }
    }

    public void addVehicleToParking(UUID parkingId, Vehicle vehicle) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);
        if (parking.isPresent()) {
            List<Vehicle> vehicles = parking.get().getAllocatedVehicles();
            vehicles.add(vehicle);
            parking.get().setAllocatedVehicles(vehicles);
            parking.get().setCurrentAvailableNumberOfSpaces(parking.get().getCurrentAvailableNumberOfSpaces() - 1);
            parkingRepository.save(parking.get());
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }

    public void removeVehicleFromParking(UUID parkingId, Vehicle vehicle) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);
        if (parking.isPresent()) {
            List<Vehicle> vehicles = parking.get().getAllocatedVehicles();
            vehicles.remove(vehicle);
            parking.get().setAllocatedVehicles(vehicles);
            parking.get().setCurrentAvailableNumberOfSpaces(parking.get().getCurrentAvailableNumberOfSpaces() + 1);
            parkingRepository.save(parking.get());
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }

    public void changeParkingMaxSpaces(UUID id, int newMaxSpaces) {
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isPresent()) {
            parking.get().setMaxNumberOfSpaces(newMaxSpaces);
            parkingRepository.save(parking.get());
        } else {
            System.out.println("Parking not found with id: " + id);
        }
    }

    public void incrementCurrentAvailableSpaces(UUID parkingId) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);
        if (parking.isPresent()) {
            int currentAvailableSpaces = parking.get().getCurrentAvailableNumberOfSpaces();
            parking.get().setCurrentAvailableNumberOfSpaces(++currentAvailableSpaces);
            parkingRepository.save(parking.get());
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }

    public void decrementCurrentAvailableSpaces(UUID parkingId) {
        Optional<Parking> parking = parkingRepository.findById(parkingId);
        if (parking.isPresent()) {
            int currentAvailableSpaces = parking.get().getCurrentAvailableNumberOfSpaces();
            if (--currentAvailableSpaces < 0) {
                System.out.println("Maximal amount of cars in the parking: " + parkingId);
            } else {
                parking.get().setCurrentAvailableNumberOfSpaces(currentAvailableSpaces);
                parkingRepository.save(parking.get());
            }
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }
}