package es.uca.dss.ParkControl.core.Subscription;

import java.util.List;
import java.util.UUID;

public interface SubscriptionTypeRepository {
    void save(SubscriptionType subscriptionType);
    SubscriptionType findById(UUID id);

    SubscriptionType findByName(String name);
    List<SubscriptionType> findAll();
    void deleteById(UUID id);
}
