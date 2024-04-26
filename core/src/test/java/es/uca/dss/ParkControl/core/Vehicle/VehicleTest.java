package es.uca.dss.ParkControl.core.Vehicle;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
public class VehicleTest {
    private Vehicle vehicle;

    @Before
    public void SetUp(){
        vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setRegistrationNumber("Example");
    }

    @Test
    public void testGettersAndSetters(){
        assertEquals(vehicle.getId(),vehicle.getId());
        assertEquals("Example",vehicle.getRegistrationNumber());
    }
}
