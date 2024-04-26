package es.uca.dss.ParkControl.core.Plan;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
public class PlanTest {
    private Plan plan;

    @Before
    public void SetUp(){
        plan = new Plan();
        plan.setId(UUID.randomUUID());
        plan.setPlanName("Example Plan");
        plan.setPrice(25.31);
        plan.setPlanType(PlanType.HOURS);
    }

    @Test
    public void testSettersAndGetters(){
        assertEquals(plan.getId(),plan.getId());
        assertEquals("Example Plan",plan.getPlanName());
        assertEquals(25.31,plan.getPrice(),0.01);
        assertEquals(PlanType.HOURS,plan.getPlanType());
    }

}
