package es.uca.dss.ParkControl.core.Plan;

import java.util.List;
import java.util.UUID;

public class PlanService {
    private PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public void createPlan(Plan plan) {
        planRepository.save(plan);
    }

    public Plan getPlan(UUID id) {
        return planRepository.findById(id);
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public void changePlanPrice(Plan plan, double newPrice) {
        Plan updatedPlan = planRepository.findById(plan.getId());
        if (updatedPlan != null){
            updatedPlan.setPrice(newPrice);
            planRepository.save(updatedPlan);
        }
        else {
            System.out.println("Plan not found");
        }
    }

    public void changePlanName(Plan plan, String newName) {
        Plan updatedPlan = planRepository.findById(plan.getId());
        if (updatedPlan != null){
            updatedPlan.setPlanName(newName);
            planRepository.save(updatedPlan);
        }
        else {
            System.out.println("Plan not found");
        }
    }
    
    public void deletePlan(UUID id) {
        planRepository.deleteById(id);
    }
}
