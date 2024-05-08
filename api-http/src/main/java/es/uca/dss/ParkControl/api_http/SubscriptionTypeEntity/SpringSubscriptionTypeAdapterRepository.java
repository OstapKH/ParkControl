package es.uca.dss.ParkControl.api_http.SubscriptionTypeEntity;

import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class SpringSubscriptionTypeAdapterRepository implements SubscriptionTypeRepository {
    private final SpringSubscriptionTypeJpaRepository repository;

    @Autowired
    public SpringSubscriptionTypeAdapterRepository(SpringSubscriptionTypeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }

    @Override
    public void save(SubscriptionType subscriptionType) {
        this.repository.save(subscriptionType);
    }

    @Override
    public Optional<SubscriptionType> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<SubscriptionType> findByName(String name) {
        List<SubscriptionType> allSubscriptions = repository.findAll();
        for (SubscriptionType subscriptionType : allSubscriptions) {
            if (subscriptionType.getName().equals(name)) {
                return Optional.of(subscriptionType);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<SubscriptionType> findAll() {
        return repository.findAll();
    }

}
