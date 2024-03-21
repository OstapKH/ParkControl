package es.uca.dss.ParkControl.core.Subscription;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.Assert.assertEquals;

public class SubscriptionTest {
    private Subscription subscription;
    private Vehicle vehicle;
    @Before
    public void SetUp(){
        subscription = new Subscription();
        vehicle = new Vehicle();
        subscription.setId(UUID.randomUUID());
        subscription.setVehicle(vehicle);
        subscription.setDateOfPurchase(LocalDate.of(2024,3,4));
        subscription.setSubscriptionType(SubscriptionType.WEEK);
    }

    @Test
    public void testSettersAndGetters(){
        assertEquals(subscription.getId(),subscription.getId());
        assertEquals(vehicle,subscription.getVehicle());
        assertEquals(LocalDate.of(2024,3,4),subscription.getDateOfPurchase());
        assertEquals(SubscriptionType.WEEK,subscription.getSubscriptionType());
    }
}
