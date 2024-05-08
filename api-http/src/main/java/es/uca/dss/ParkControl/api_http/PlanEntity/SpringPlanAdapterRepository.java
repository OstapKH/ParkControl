package es.uca.dss.ParkControl.api_http.PlanEntity;

import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpringPlanAdapterRepository implements PlanRepository {

    private final SpringPlanJpaRepository springPlanJpaRepository;

    @Autowired
    public SpringPlanAdapterRepository(SpringPlanJpaRepository springPlanJpaRepository) {
        this.springPlanJpaRepository = springPlanJpaRepository;
    }

    @Override
    public void save(Plan plan) {
        springPlanJpaRepository.save(plan);
    }

    @Override
    public Plan findById(UUID id) {
        return springPlanJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Plan> findAll() {
        return springPlanJpaRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        springPlanJpaRepository.deleteById(id);
    }
}
