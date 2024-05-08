package es.uca.dss.ParkControl.core.Parking;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Parking getParkingById(UUID id) {
        return parkingRepository.findById(id);
    }

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public void deleteParking(UUID id) {
        parkingRepository.deleteById(id);
    }

    public void changeParkingName(UUID id, String newName) {
        Parking parking = parkingRepository.findById(id);
        if (parking != null) {
            parking.setName(newName);
            parkingRepository.save(parking);
        } else {
            System.out.println("Parking not found with id: " + id);
        }
    }

    public void addVehicleToParking(UUID parkingId, Vehicle vehicle) {

        Parking parking = parkingRepository.findById(parkingId);
        if (parking != null) {
            List<Vehicle> vehicles = parking.getAllocatedVehicles();
            vehicles.add(vehicle);
            parking.setAllocatedVehicles(vehicles);
            parking.setCurrentAvailableNumberOfSpaces(parking.getCurrentAvailableNumberOfSpaces() - 1);
            parkingRepository.save(parking);
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }

    public void removeVehicleFromParking(UUID parkingId, Vehicle vehicle) {
        Parking parking = parkingRepository.findById(parkingId);
        if (parking != null) {
            List<Vehicle> vehicles = parking.getAllocatedVehicles();
            vehicles.remove(vehicle);
            parking.setAllocatedVehicles(vehicles);
            parkingRepository.save(parking);
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }

    public void changeParkingMaxSpaces(UUID id, int newMaxSpaces) {
        Parking parking = parkingRepository.findById(id);
        if (parking != null) {
            parking.setMaxNumberOfSpaces(newMaxSpaces);
            parkingRepository.save(parking);
        } else {
            System.out.println("Parking not found with id: " + id);
        }
    }

    public void incrementCurrentAvailableSpaces(UUID parkingId) {
        Parking parking = parkingRepository.findById(parkingId);
        if (parking != null) {
            int currentAvailableSpaces = parking.getCurrentAvailableNumberOfSpaces();
            parking.setCurrentAvailableNumberOfSpaces(++currentAvailableSpaces);
            parkingRepository.save(parking);
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }

    public void decrementCurrentAvailableSpaces(UUID parkingId) {
        Parking parking = parkingRepository.findById(parkingId);
        if (parking != null) {
            int currentAvailableSpaces = parking.getCurrentAvailableNumberOfSpaces();
            if (--currentAvailableSpaces < 0) {
                System.out.println("Maximal amount of cars in the parking: " + parkingId);
            } else {
                parking.setCurrentAvailableNumberOfSpaces(currentAvailableSpaces);
                parkingRepository.save(parking);
            }
        } else {
            System.out.println("Parking not found with id: " + parkingId);
        }
    }
}
