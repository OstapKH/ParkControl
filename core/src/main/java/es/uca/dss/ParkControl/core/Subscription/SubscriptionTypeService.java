package es.uca.dss.ParkControl.core.Subscription;

import java.util.List;
import java.util.UUID;

public class SubscriptionTypeService {
    private SubscriptionTypeRepository repository;

    public SubscriptionTypeService(SubscriptionTypeRepository repository) {
        this.repository = repository;
    }

    public void saveSubscriptionType(SubscriptionType subscriptionType) {
        repository.save(subscriptionType);
    }

    public SubscriptionType getSubscription(UUID id) {
        return repository.findById(id);
    }

    public SubscriptionType getSubscriptionByName(String name) {
        return repository.findByName(name);
    }

    public List<SubscriptionType> getAllSubscriptions() {
        return repository.findAll();
    }

    public void deleteSubscription(UUID id) {
        repository.deleteById(id);
    }
}
