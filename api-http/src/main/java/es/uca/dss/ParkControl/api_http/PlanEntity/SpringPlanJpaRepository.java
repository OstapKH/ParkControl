package es.uca.dss.ParkControl.api_http.PlanEntity;

import es.uca.dss.ParkControl.core.Plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface SpringPlanJpaRepository extends JpaRepository<Plan, UUID> {
}
