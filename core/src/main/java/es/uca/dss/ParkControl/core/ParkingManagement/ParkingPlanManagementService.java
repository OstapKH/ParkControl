package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanRepository;
import es.uca.dss.ParkControl.core.Plan.PlanService;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParkingPlanManagementService {
    private PlanService planService;

    public ParkingPlanManagementService(PlanRepository planRepository) {
        this.planService = new PlanService(planRepository);
    }

    // Method to create a plan
    public void createPlan(String name, double price, String planType) {
        Plan plan = new Plan();
        plan.setId(UUID.randomUUID());
        plan.setPlanName(name);
        plan.setPrice(price);

        plan.setPlanType(stringToPlanType(planType));

        planService.createPlan(plan);
    }

    public PlanType stringToPlanType(String s) {
            return PlanType.valueOf(s.toUpperCase());
    }

    // Method to get a plan by name
    public Plan getPlanByName(String name) {
        return planService.getPlanByName(name);
    }

    //Method to get a plan by id
    public Plan getPlanById(UUID id) {
        return planService.getPlan(id);
    }

    // Method to change plan price
    public void changePlanPrice(String name, double newPrice) {
        Plan plan = planService.getPlanByName(name);
        planService.changePlanPrice(plan, newPrice);
    }

    // Method to change plan name
    public void changePlanName(String name, String newName) {
        Plan plan = planService.getPlanByName(name);
        planService.changePlanName(plan, newName);
    }

    // Method to delete a plan
    public void deletePlan(String name) {
        Plan plan = planService.getPlanByName(name);
        planService.deletePlan(plan.getId());
    }

    // Method to get all plans
    public Iterable<Plan> getAllPlans() {
        return planService.getAllPlans();
    }
}
