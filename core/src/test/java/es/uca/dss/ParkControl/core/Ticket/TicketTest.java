package es.uca.dss.ParkControl.core.Ticket;
import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.UUID;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
public class TicketTest {
    private Ticket ticket;
    private Vehicle vehicle;
    private Plan plan;
    @Before
    public void SetUp(){
//        vehicle = new Vehicle();
//        plan = new Plan();
//        ticket = new Ticket();
//        ticket.setId(UUID.randomUUID());
//        ticket.setPlan(plan);
//        ticket.setVehicle(vehicle);
//        ticket.setDateOfIssue(LocalDateTime.of(2024,3,4));
//        ticket.setDateOfPayment(LocalDateTime.of(2024,3,5));
    }

    @Test
    public void testSettersAndGetters(){
        assertEquals(ticket.getId(),ticket.getId());
        assertEquals(vehicle,ticket.getVehicle());
        assertEquals(plan,ticket.getPlan());
        assertEquals(LocalDate.of(2024,3,4),ticket.getDateOfIssue());
        assertEquals(LocalDate.of(2024,3,5),ticket.getDateOfPayment());



    }
}
