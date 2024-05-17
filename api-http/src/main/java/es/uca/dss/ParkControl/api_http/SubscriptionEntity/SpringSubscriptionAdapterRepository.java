package es.uca.dss.ParkControl.api_http.SubscriptionEntity;

import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionRepository;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpringSubscriptionAdapterRepository implements SubscriptionRepository {
    private final SpringSubscriptionJpaRepository repository;

    @Autowired
    public SpringSubscriptionAdapterRepository(SpringSubscriptionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Subscription subscription) {
        repository.save(subscription);
    }

    @Override
    public Subscription findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Subscription findByType(SubscriptionType subscriptionType) {
        List<Subscription> allSubscriptions = repository.findAll();
        for (Subscription subscription : allSubscriptions) {
            if (subscription.getSubscriptionType().equals(subscriptionType)) {
                return subscription;
            }
        }
        return null;
    }

    @Override
    public List<Subscription> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Subscription findSubscriptionByRegistrationNumber(String registrationNumber) {
        List<Subscription> allSubscriptions = repository.findAll();
        for (Subscription subscription : allSubscriptions) {
            if (subscription.getVehicle().getRegistrationNumber().equals(registrationNumber)) {
                return subscription;
            }
        }
        return null;
    }

    @Override
    public Subscription findSubscriptionByVehicleId(UUID vehicleId) {
        List<Subscription> allSubscriptions = repository.findAll();
        for (Subscription subscription : allSubscriptions) {
            if (subscription.getVehicle().getId().equals(vehicleId)) {
                return subscription;
            }
        }
        return null;
    }
}
