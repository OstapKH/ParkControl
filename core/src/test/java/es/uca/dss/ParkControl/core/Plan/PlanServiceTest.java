package es.uca.dss.ParkControl.core.Plan;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
public class PlanServiceTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PlanService planService;
    private PlanRepository planRepository;
    private Plan plan;

    @Before
    public void SetUp(){
        planRepository = new InMemoryPlanRepository();
        planService = new PlanService(planRepository);

        plan = new Plan();
        plan.setId(UUID.randomUUID());
        plan.setPlanName("Example Plan");
        plan.setPrice(25.31);
        plan.setPlanType(PlanType.WEEKS);
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testCreatePlan(){
        planService.createPlan(plan);
        List<Plan> plans = planRepository.findAll();
        assertEquals(1,plans.size());

        Plan retrievedPlan = planRepository.findById(plan.getId());
        assertEquals(retrievedPlan,plan);
    }

    @Test
    public void testGetPlan(){
        planService.createPlan(plan);
        Plan retrievedPlan = planService.getPlan(plan.getId());
        assertEquals(retrievedPlan,plan);
    }

    @Test
    public void testGetAllPlans(){
        List<Plan> plans = planService.getAllPlans();
        assertNotNull(plans); //Entendemos null como no crear la lista
        assertEquals(0,plans.size());
    }

    @Test
    public void testChangePlanPrice(){
        System.setOut(new PrintStream(outputStreamCaptor));

        Plan invalidPlan = new Plan();
        UUID invalidUUID = UUID.randomUUID();
        invalidPlan.setId(invalidUUID);

        //CASE INCORRECT PLAN
        planService.changePlanPrice(invalidPlan,25.1);
        assertEquals("Plan not found" + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();

        //CASE CORRECT PLAN
        planService.createPlan(plan);
        planService.changePlanPrice(plan,20.20);
        Plan retrievedPlan = planService.getPlan(plan.getId());
        assertEquals(20.20,plan.getPrice(),0.01);
    }

    @Test
    public void testChangePlanName(){
        System.setOut(new PrintStream(outputStreamCaptor));

        Plan invalidPlan = new Plan();
        UUID invalidUUID = UUID.randomUUID();
        invalidPlan.setId(invalidUUID);

        //CASE INCORRECT PLAN
        planService.changePlanName(invalidPlan,"Example2");
        assertEquals("Plan not found" + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();

        //CASE CORRECT PLAN
        planService.createPlan(plan);
        planService.changePlanName(plan,"Example2");
        assertEquals("Example2",plan.getPlanName());

    }

    @Test
    public void testDeletePlan(){
        planService.createPlan(plan);
        planService.deletePlan(plan.getId());
        List<Plan> plans = planService.getAllPlans();
        assertEquals(0,plans.size());
    }
}
