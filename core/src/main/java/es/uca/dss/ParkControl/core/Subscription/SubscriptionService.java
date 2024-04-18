package es.uca.dss.ParkControl.core.Subscription;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SubscriptionService {
    private SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public void createSubscription(Subscription subscription) {
        repository.save(subscription);
    }

    public Subscription getSubscription(UUID id) {
        return repository.findById(id);
    }

    public Subscription getSubscriptionByType(SubscriptionType subscriptionType) {
        return repository.findByType(subscriptionType);
    }

    public boolean isValidSubscriptionAvailable(String registrationNumber){
        Subscription subscription = repository.findSubscriptionByRegistrationNumber(registrationNumber);
        return subscription!= null && subscription.getDateOfPurchase().isBefore(LocalDateTime.now());
    }

    public List<Subscription> getAllSubscriptions() {
        return repository.findAll();
    }

    public void deleteSubscription(UUID id) {
        repository.deleteById(id);
    }
}
