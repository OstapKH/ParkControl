package es.uca.dss.ParkControl.core.Ticket;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class InMemoryTicketRepositoryTest {

    private InMemoryTicketRepository inMemoryTicketRepository;
    private Ticket ticket;
    @Before
    public void SetUp(){
        inMemoryTicketRepository = new InMemoryTicketRepository();
        ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
    }

    @Test
    public void testSave(){
        inMemoryTicketRepository.save(ticket);
        List<Ticket> tickets = inMemoryTicketRepository.findAll();
        assertEquals(1,tickets.size());
        Ticket retrievedTicket = inMemoryTicketRepository.findById(ticket.getId());
        assertEquals(retrievedTicket,ticket);
    }

    @Test
    public void testFindById(){
        inMemoryTicketRepository.save(ticket);
        Ticket retrievedticket = inMemoryTicketRepository.findById(ticket.getId());
        assertEquals(retrievedticket,ticket);
    }

    @Test
    public void testFindAll(){
        List<Ticket> tickets = inMemoryTicketRepository.findAll();
        assertNotNull(tickets);
        assertEquals(0,tickets.size());
    }

    @Test
    public void testDeleteById(){
        inMemoryTicketRepository.save(ticket);
        inMemoryTicketRepository.deleteById(ticket.getId());
        List<Ticket> tickets = inMemoryTicketRepository.findAll();
        assertEquals(0,tickets.size());
    }
}
