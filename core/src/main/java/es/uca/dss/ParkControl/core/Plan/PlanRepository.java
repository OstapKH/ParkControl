package es.uca.dss.ParkControl.core.Plan;


import java.util.List;
import java.util.UUID;

public interface PlanRepository {
    void save(Plan plan);
    Plan findById(UUID id);
    List<Plan> findAll();
    void deleteById(UUID id);
}
