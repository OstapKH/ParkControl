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
        vehicle = new Vehicle();
        plan = new Plan();
        ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setPlan(plan);
        ticket.setVehicle(vehicle);
        ticket.setDateOfIssue(LocalDate.parse("2024-03-04").atStartOfDay());
        ticket.setDateOfPayment(LocalDate.parse("2024-03-05").atStartOfDay());
    }

    @Test
    public void testSettersAndGetters(){
        assertEquals(ticket.getId(),ticket.getId());
        assertEquals(vehicle,ticket.getVehicle());
        assertEquals(plan,ticket.getPlan());
        assertEquals(LocalDate.parse("2024-03-04").atStartOfDay(),ticket.getDateOfIssue());
        assertEquals(LocalDate.parse("2024-03-05").atStartOfDay(),ticket.getDateOfPayment());



    }
}
