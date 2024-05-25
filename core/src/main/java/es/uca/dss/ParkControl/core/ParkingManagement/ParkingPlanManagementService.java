package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanRepository;
import es.uca.dss.ParkControl.core.Plan.PlanService;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for managing parking plan operations.
 */
@Service
public class ParkingPlanManagementService {
    private PlanService planService;

    /**
     * Constructor for the ParkingPlanManagementService.
     *
     * @param planRepository the plan repository
     */
    public ParkingPlanManagementService(PlanRepository planRepository) {
        this.planService = new PlanService(planRepository);
    }

    /**
     * Method to create a plan.
     *
     * @param name the name of the plan
     * @param price the price of the plan
     * @param planType the type of the plan
     */
    public void createPlan(String name, double price, String planType) {
        Plan plan = new Plan();
        plan.setId(UUID.randomUUID());
        plan.setPlanName(name);
        plan.setPrice(price);

        plan.setPlanType(stringToPlanType(planType));

        planService.createPlan(plan);
    }

    /**
     * Method to convert a string to a PlanType.
     *
     * @param s the string to convert
     * @return the PlanType
     */
    public PlanType stringToPlanType(String s) {
        return PlanType.valueOf(s.toUpperCase());
    }

    /**
     * Method to get a plan by name.
     *
     * @param name the name of the plan
     * @return the plan
     */
    public Plan getPlanByName(String name) {
        return planService.getPlanByName(name);
    }

    /**
     * Method to get a plan by id.
     *
     * @param id the id of the plan
     * @return the plan
     */
    public Plan getPlanById(UUID id) {
        return planService.getPlan(id);
    }

    /**
     * Method to change the price of a plan.
     *
     * @param name the name of the plan
     * @param newPrice the new price of the plan
     */
    public void changePlanPrice(String name, double newPrice) {
        Plan plan = planService.getPlanByName(name);
        planService.changePlanPrice(plan, newPrice);
    }

    /**
     * Method to change the name of a plan.
     *
     * @param name the current name of the plan
     * @param newName the new name of the plan
     */
    public void changePlanName(String name, String newName) {
        Plan plan = planService.getPlanByName(name);
        planService.changePlanName(plan, newName);
    }

    /**
     * Method to delete a plan.
     *
     * @param name the name of the plan
     */
    public void deletePlan(String name) {
        Plan plan = planService.getPlanByName(name);
        planService.deletePlan(plan.getId());
    }

    /**
     * Method to get all plans.
     *
     * @return all plans
     */
    public Iterable<Plan> getAllPlans() {
        return planService.getAllPlans();
    }
}