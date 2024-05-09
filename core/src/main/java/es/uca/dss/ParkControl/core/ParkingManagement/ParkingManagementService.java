package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Parking.ParkingRepository;
import es.uca.dss.ParkControl.core.Parking.ParkingService;
import org.springframework.stereotype.Service;

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
        }
    }

    public Parking getParkingById(UUID parkingId) {
        return parkingService.getParkingById(parkingId);
    }
    // TODO Check if Optional would be suitable here.

}
