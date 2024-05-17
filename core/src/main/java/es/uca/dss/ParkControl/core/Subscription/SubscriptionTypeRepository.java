package es.uca.dss.ParkControl.core.Subscription;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionTypeRepository {
    void deleteById(UUID id);

    void save(SubscriptionType subscriptionType);

    Optional<SubscriptionType> findById(UUID id);

    Optional<SubscriptionType> findByName(String name);
    List<SubscriptionType> findAll();
}
