package es.uca.dss.ParkControl.core.Plan;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
public class InMemoryPlanRepositoryTest {

    private  InMemoryPlanRepository inMemoryPlanRepository;
    private Plan plan;
    @Before
    public void SetUp(){
        inMemoryPlanRepository = new InMemoryPlanRepository();

        plan = new Plan();
        plan.setId(UUID.randomUUID());
        plan.setPlanName("Example Plan");
        plan.setPrice(25.31);
        plan.setPlanType(PlanType.DAYS);
    }

    @Test
    public void testSave(){
        inMemoryPlanRepository.save(plan);
        List<Plan> plans = inMemoryPlanRepository.findAll();
        assertEquals(1, plans.size());
    }

    @Test
    public void testFindById(){
        inMemoryPlanRepository.save(plan);
        Plan retrievingPlan = inMemoryPlanRepository.findById(plan.getId());
        assertEquals(retrievingPlan,plan);
    }

    @Test
    public void testFindAll(){
        List<Plan> plans = inMemoryPlanRepository.findAll();
        assertEquals(0,plans.size());
    }

    @Test
    public void testDeleteById(){
    inMemoryPlanRepository.save(plan);
    inMemoryPlanRepository.deleteById(plan.getId());;
    List<Plan> plans = inMemoryPlanRepository.findAll();
    assertEquals(0,plans.size());
    }
}
