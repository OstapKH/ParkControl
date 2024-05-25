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

/**
 * Service for managing parking operations.
 */
@Service
public class ParkingManagementService {

    // Creating the services to manage the data
    private ParkingService parkingService;

    /**
     * Constructor for the ParkingManagementService.
     *
     * @param parkingRepository the parking repository
     */
    public ParkingManagementService(ParkingRepository parkingRepository) {
        // Creating the services to manage the data
        this.parkingService = new ParkingService(parkingRepository);
    }

    /**
     * Method to create a parking.
     *
     * @param name the name of the parking
     * @param maxNumberOfSpaces the maximum number of spaces in the parking
     * @param zipCode the zip code of the parking
     * @return the id of the created parking
     */
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

    /**
     * Method to change parking details.
     *
     * @param id the id of the parking
     * @param newName the new name of the parking
     * @param newAmountOfSpaces the new amount of spaces in the parking
     * @param newZipCode the new zip code of the parking
     */
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

    /**
     * Method to get all parkings.
     *
     * @return all parkings
     */
    public Iterable<Parking> getAllParkings() {
        return parkingService.getAllParkings();
    }

    /**
     * Method to delete a parking.
     *
     * @param parkingId the id of the parking to delete
     */
    public void deleteParking(UUID parkingId) {
        parkingService.deleteParking(parkingId);
    }

    /**
     * Method to get all allocated vehicles in a parking.
     *
     * @param parkingId the id of the parking
     * @return all allocated vehicles in the parking
     */
    public List<Vehicle> getAllAllocatedVehiclesInParking(UUID parkingId) {
        Optional<Parking> optionalParking = parkingService.getParkingById(parkingId);
        if (optionalParking.isPresent()) {
            return optionalParking.get().getAllocatedVehicles();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking not found");
        }
    }

    /**
     * Method to get a parking by its id.
     *
     * @param parkingId the id of the parking
     * @return the parking with the given id
     */
    public Optional<Parking> getParkingById(UUID parkingId) {
        return parkingService.getParkingById(parkingId);
    }
}