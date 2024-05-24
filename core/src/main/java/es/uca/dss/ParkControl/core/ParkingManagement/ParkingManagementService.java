package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Parking.ParkingRepository;
import es.uca.dss.ParkControl.core.Parking.ParkingService;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
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
        Optional<Parking> optionalParking = parkingService.getParkingById(id);
        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
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
    public List<Vehicle> getAllAllocatedVehiclesInParking(UUID parkingId) {
        Optional<Parking> optionalParking = parkingService.getParkingById(parkingId);
        if (optionalParking.isPresent()) {
            return optionalParking.get().getAllocatedVehicles();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking not found");
        }
    }

    public Optional<Parking> getParkingById(UUID parkingId) {
        return parkingService.getParkingById(parkingId);
    }
    // TODO Check if Optional would be suitable here.

}
