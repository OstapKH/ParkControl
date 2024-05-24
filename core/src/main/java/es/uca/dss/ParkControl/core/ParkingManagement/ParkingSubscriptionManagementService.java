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

@Service
public class ParkingSubscriptionManagementService {

    private SubscriptionService subscriptionService;
    private VehicleService vehicleService;

    private SubscriptionTypeService subscriptionTypeService;


    public ParkingSubscriptionManagementService(SubscriptionRepository subscriptionRepository, VehicleRepository vehicleRepository, SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionService = new SubscriptionService(subscriptionRepository);
        this.vehicleService = new VehicleService(vehicleRepository);
        this.subscriptionTypeService = new SubscriptionTypeService(subscriptionTypeRepository);
    }

    // Method to subscribe a vehicle
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

    // Method to create a subscription type
    public UUID createSubscriptionType(String name, double price) {
        SubscriptionType subscriptionType = new SubscriptionType();
        // TODO Ask about this method
        subscriptionType.setId(UUID.randomUUID());
        subscriptionType.setName(name);
        subscriptionType.setPrice(price);
        subscriptionTypeService.saveSubscriptionType(subscriptionType);
        return subscriptionType.getId();
    }

    // Method to change the price of a subscription type
    public void changeSubscriptionTypePrice(String name, double newPrice) {
        Optional<SubscriptionType> subscriptionTypeOptional = subscriptionTypeService.getSubscriptionByName(name);
        if (subscriptionTypeOptional.isPresent()) {
            SubscriptionType subscriptionType = subscriptionTypeOptional.get();
            subscriptionType.setPrice(newPrice);
            subscriptionTypeService.saveSubscriptionType(subscriptionType);
        }
    }


    // Method to get the exits statistic by year
    public Optional<SubscriptionType> getSubscriptionTypeById(UUID id) {
        return subscriptionTypeService.getSubscription(id);
    }

    // Method to get the subscription type by id
    public Subscription getSubscriptionById(UUID id) {
        return subscriptionService.getSubscription(id);
    }

    public void deleteSubscriptionType(String name) {
        Optional<SubscriptionType> subscriptionTypeOptional = subscriptionTypeService.getSubscriptionByName(name);
        if (subscriptionTypeOptional.isPresent()) {
            SubscriptionType subscriptionType = subscriptionTypeOptional.get();
            subscriptionTypeService.deleteSubscription(subscriptionType.getId());
        }
    }

    public List<SubscriptionType> getAllSubscriptionTypes() {
        return subscriptionTypeService.getAllSubscriptions();
    }

    public SubscriptionType getSubscriptionTypeByName(String name) {
        return subscriptionTypeService.getSubscriptionByName(name).orElse(null);
    }

    public Iterable<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }
}
