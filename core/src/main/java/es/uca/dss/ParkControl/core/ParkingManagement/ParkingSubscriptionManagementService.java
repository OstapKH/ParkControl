package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Subscription.*;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import es.uca.dss.ParkControl.core.Vehicle.VehicleRepository;
import es.uca.dss.ParkControl.core.Vehicle.VehicleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing parking subscriptions.
 */
@Service
public class ParkingSubscriptionManagementService {

    private SubscriptionService subscriptionService;
    private VehicleService vehicleService;

    private SubscriptionTypeService subscriptionTypeService;


    /**
     * Constructor for the ParkingSubscriptionManagementService.
     *
     * @param subscriptionRepository     the subscription repository
     * @param vehicleRepository          the vehicle repository
     * @param subscriptionTypeRepository the subscription type repository
     */
    public ParkingSubscriptionManagementService(SubscriptionRepository subscriptionRepository, VehicleRepository vehicleRepository, SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionService = new SubscriptionService(subscriptionRepository);
        this.vehicleService = new VehicleService(vehicleRepository);
        this.subscriptionTypeService = new SubscriptionTypeService(subscriptionTypeRepository);
    }

    /**
     * Method to subscribe a vehicle.
     *
     * @param registrationNumber the registration number of the vehicle
     * @param subscriptionType   the type of the subscription
     * @return the subscription
     */
    public Subscription subscribeVehicle(String registrationNumber, SubscriptionType subscriptionType) {
        Subscription subscription = new Subscription();
        Vehicle vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setId(UUID.randomUUID());
            vehicle.setRegistrationNumber(registrationNumber);
            vehicleService.createVehicle(vehicle);
            subscription.setVehicle(vehicle);
        } else {
            subscription.setVehicle(vehicle);
        }
        subscription.setId(UUID.randomUUID());
        subscription.setSubscriptionType(subscriptionType);
        subscription.setDateOfPurchase(null);
        subscriptionService.createSubscription(subscription);
        return subscription;
    }

    /**
     * Method to create a subscription type.
     *
     * @param name  the name of the subscription type
     * @param price the price of the subscription type
     * @return the id of the created subscription type
     */
    public UUID createSubscriptionType(String name, double price) {
        SubscriptionType subscriptionType = new SubscriptionType();
        // TODO Ask about this method
        subscriptionType.setId(UUID.randomUUID());
        subscriptionType.setName(name);
        subscriptionType.setPrice(price);
        subscriptionTypeService.saveSubscriptionType(subscriptionType);
        return subscriptionType.getId();
    }

    /**
     * Method to change the price of a subscription type.
     *
     * @param name     the name of the subscription type
     * @param newPrice the new price of the subscription type
     */
    public void changeSubscriptionTypePrice(String name, double newPrice) {
        Optional<SubscriptionType> subscriptionTypeOptional = subscriptionTypeService.getSubscriptionByName(name);
        if (subscriptionTypeOptional.isPresent()) {
            SubscriptionType subscriptionType = subscriptionTypeOptional.get();
            subscriptionType.setPrice(newPrice);
            subscriptionTypeService.saveSubscriptionType(subscriptionType);
        }
    }


    /**
     * Method to get a subscription type by id.
     *
     * @param id the id of the subscription type
     * @return the subscription type
     */
    public Optional<SubscriptionType> getSubscriptionTypeById(UUID id) {
        return subscriptionTypeService.getSubscription(id);
    }

    /**
     * Method to get a subscription by id.
     *
     * @param id the id of the subscription
     * @return the subscription
     */
    public Subscription getSubscriptionById(UUID id) {
        return subscriptionService.getSubscription(id);
    }

    /**
     * Method to delete a subscription type.
     *
     * @param name the name of the subscription type
     */
    public void deleteSubscriptionType(String name) {
        Optional<SubscriptionType> subscriptionTypeOptional = subscriptionTypeService.getSubscriptionByName(name);
        if (subscriptionTypeOptional.isPresent()) {
            SubscriptionType subscriptionType = subscriptionTypeOptional.get();
            subscriptionTypeService.deleteSubscription(subscriptionType.getId());
        }
    }

    /**
     * Method to get all subscription types.
     *
     * @return all subscription types
     */
    public List<SubscriptionType> getAllSubscriptionTypes() {
        return subscriptionTypeService.getAllSubscriptions();
    }

    /**
     * Method to get a subscription type by name.
     *
     * @param name the name of the subscription type
     * @return the subscription type
     */
    public SubscriptionType getSubscriptionTypeByName(String name) {
        return subscriptionTypeService.getSubscriptionByName(name).orElse(null);
    }

    /**
     * Method to get all subscriptions.
     *
     * @return all subscriptions
     */
    public Iterable<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }
}
