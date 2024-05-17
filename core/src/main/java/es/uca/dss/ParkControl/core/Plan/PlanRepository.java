package es.uca.dss.ParkControl.core.Plan;


import java.util.List;
import java.util.UUID;

public interface PlanRepository {
    void save(Plan plan);
    Plan findById(UUID id);

    Plan findByName(String name);
    List<Plan> findAll();
    void deleteById(UUID id);
}
