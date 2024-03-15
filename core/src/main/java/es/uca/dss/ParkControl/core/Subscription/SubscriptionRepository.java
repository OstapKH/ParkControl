package es.uca.dss.ParkControl.core.Subscription;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository {
    void save(Subscription subscription);
    Subscription findById(UUID id);
    List<Subscription> findAll();
    void deleteById(UUID id);
}
