package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Parking.ParkingRepository;
import es.uca.dss.ParkControl.core.Parking.ParkingService;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParkingManagementService {

    // Creating the services to manage the data
    private ParkingService parkingService;

    //Service constructor
    public ParkingManagementService(ParkingRepository parkingRepository) {
        // Creating the services to manage the data
        this.parkingService = new ParkingService(parkingRepository);
    }

    // Method to create a parking
    public UUID createParking(String name, int maxNumberOfSpaces, String zipCode) {
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName(name);
        parking.setMaxNumberOfSpaces(maxNumberOfSpaces);
        parking.setCurrentAvailableNumberOfSpaces(maxNumberOfSpaces);
        parking.setZipCode(zipCode);
        parkingService.saveParking(parking);
        return parking.getId();
    }

    // Method to change parking details
    public void changeParkingDetails(UUID id, String newName, int newAmountOfSpaces, String newZipCode) {
        Parking parking = parkingService.getParkingById(id);
        if (parking != null) {
            parking.setName(newName);
            parking.setZipCode(newZipCode);
            parking.setMaxNumberOfSpaces(newAmountOfSpaces);
            if (newAmountOfSpaces - parking.getAllocatedVehicles().size() < 0) {
                parking.setCurrentAvailableNumberOfSpaces(0);
            } else {
                parking.setCurrentAvailableNumberOfSpaces(newAmountOfSpaces);
            }
            parkingService.saveParking(parking);
        }
    }

    // Method to get all parkings
    public Iterable<Parking> getAllParkings() {
        return parkingService.getAllParkings();
    }

    // Method to delete a parking
    public void deleteParking(UUID parkingId) {
        parkingService.deleteParking(parkingId);
    }

    // Method to get all allocated vehicles in a parking
    public Iterable<Vehicle> getAllAllocatedVehiclesInParking(UUID parkingId) {
        return parkingService.getParkingById(parkingId).getAllocatedVehicles();
    }

    public Parking getParkingById(UUID parkingId) {
        return parkingService.getParkingById(parkingId);
    }
    // TODO Check if Optional would be suitable here.

}
