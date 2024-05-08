package es.uca.dss.ParkControl.core.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionTypeService {
    private SubscriptionTypeRepository repository;

    public SubscriptionTypeService(SubscriptionTypeRepository repository) {
        this.repository = repository;
    }

    public void saveSubscriptionType(SubscriptionType subscriptionType) {
        repository.save(subscriptionType);
    }

    public Optional<SubscriptionType> getSubscription(UUID id) {
        return repository.findById(id);
    }

    public Optional<SubscriptionType> getSubscriptionByName(String name) {
        return repository.findByName(name);
    }

    public List<SubscriptionType> getAllSubscriptions() {
        return repository.findAll();
    }

    public void deleteSubscription(UUID id) {
        repository.deleteById(id);
    }
}
