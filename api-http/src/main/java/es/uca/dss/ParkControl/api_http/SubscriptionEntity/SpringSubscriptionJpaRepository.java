package es.uca.dss.ParkControl.api_http.SubscriptionEntity;

import es.uca.dss.ParkControl.core.Subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface SpringSubscriptionJpaRepository extends JpaRepository<Subscription, UUID>{
}
