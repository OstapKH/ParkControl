package es.uca.dss.ParkControl.api_http.SubscriptionTypeEntity;

import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionTypeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringSubscriptionTypeJpaRepository extends JpaRepository<SubscriptionType, UUID> {

}
