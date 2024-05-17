package es.uca.dss.ParkControl.core.Subscription;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemorySubscriptionRepository implements SubscriptionRepository{
    private List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public void save(Subscription subscription) {
        UUID id = subscription.getId();
        for (int i = 0; i < subscriptions.size(); i++) {
            if (subscriptions.get(i).getId().equals(id)) {
                subscriptions.set(i, subscription);
                return;
            }
        }
        subscriptions.add(subscription);
    }

    @Override
    public Subscription findById(UUID id) {
        for (Subscription subscription : subscriptions) {
            if (subscription.getId().equals(id)) {
                return subscription;
            }
        }
        return null;
    }

    @Override
    public Subscription findByType(SubscriptionType subscriptionType) {
        return subscriptions.stream()
                .filter(subscription -> subscription.getSubscriptionType().equals(subscriptionType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptions;
    }

    @Override
    public void deleteById(UUID id) {
        subscriptions.removeIf(subscription -> subscription.getId().equals(id));
    }

    @Override
    public Subscription findSubscriptionByRegistrationNumber(String registrationNumber) {
        for (Subscription subscription: subscriptions){
            if (subscription.getVehicle().getRegistrationNumber().equals(registrationNumber)){
                return subscription;
            }
        }
        return null;
    }

    @Override
    public Subscription findSubscriptionByVehicleId(UUID vehicleId) {
        return subscriptions.stream()
                .filter(subscription -> subscription.getVehicle().getId().equals(vehicleId))
                .findFirst()
                .orElse(null);
    }
}
