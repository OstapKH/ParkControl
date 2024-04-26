package es.uca.dss.ParkControl.core.Subscription;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository {
    void save(Subscription subscription);
    Subscription findById(UUID id);

    Subscription findByType(SubscriptionType subscriptionType);
    List<Subscription> findAll();
    void deleteById(UUID id);

    Subscription findSubscriptionByRegistrationNumber(String registrationNumber);
}
