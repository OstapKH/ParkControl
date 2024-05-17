package es.uca.dss.ParkControl.core.Plan;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryPlanRepository implements PlanRepository {
    private List<Plan> plans;

    public InMemoryPlanRepository() {
        this.plans = new ArrayList<>();
    }

    @Override
    public void save(Plan plan) {
        UUID id = plan.getId();
        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i).getId().equals(id)) {
                plans.set(i, plan); // Update existing parking entry
                return;
            }
        }
        plans.add(plan);
    }

    @Override
    public Plan findById(UUID id) {
        for (Plan plan : plans) {
            if (plan.getId().equals(id)) {
                return plan;
            }
        }
        return null;
    }

    @Override
    public Plan findByName(String name) {
        for (Plan plan : plans) {
            if (plan.getPlanName().equals(name)) {
                return plan;
            }
        }
        return null;
    }

    @Override
    public List<Plan> findAll() {
        return plans;
    }

    @Override
    public void deleteById(UUID id) {
        plans.removeIf(plan -> plan.getId().equals(id));
    }
}